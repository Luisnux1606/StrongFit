/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import assets.Calculos;
import assets.PrtoSerial;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPErrorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPErrorEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPSensorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import consultas.ConsBuscarVentas;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import modelos.Conexion;
import vistas.VisFicha;

/**
 *
 * @author Administrator
 */
public class CtrlRegistroEntrada implements ActionListener {
    
    VisFicha visFicha;
    Conexion con=new Conexion();
    PrtoSerial prtoSer = new PrtoSerial();
    javax.swing.Timer tPort; 
    
    //Varible que permite iniciar el dispositivo de lector de huella conectado
    // con sus distintos metodos.
    public DPFPCapture Lector = DPFPGlobal.getCaptureFactory().createCapture();

    //Varible que permite establecer las capturas de la huellas, para determina sus caracteristicas
    // y poder estimar la creacion de un template de la huella para luego poder guardarla
    private DPFPEnrollment Reclutador = DPFPGlobal.getEnrollmentFactory().createEnrollment();

    //Esta variable tambien captura una huella del lector y crea sus caracteristcas para auntetificarla
    // o verificarla con alguna guardada en la BD
    private DPFPVerification Verificador = DPFPGlobal.getVerificationFactory().createVerification();

    //Variable que para crear el template de la huella luego de que se hallan creado las caracteriticas
    // necesarias de la huella si no ha ocurrido ningun problema
    private DPFPTemplate template;
    public static String TEMPLATE_PROPERTY = "template";
    
    public CtrlRegistroEntrada(VisFicha visFicha)
    {
        this.visFicha = visFicha;
        this.visFicha.btnAbrir.addActionListener(this);
        this.visFicha.btnCerrar.addActionListener(this);
        
        tPort =   new javax.swing.Timer(0,null);
        
        javax.swing.Timer t = new javax.swing.Timer(1000, new ActionListener() {
            
        public void actionPerformed(ActionEvent e) 
        {
            Date fecha = new Date(); //aqui tu fecha;
            SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
            String fechaa=dt.format(fecha);
            visFicha.jftFechaActual.setText(fechaa);
            dt = new SimpleDateFormat("hh:mm:ss");
            String hora=dt.format(fecha);
            visFicha.jftHoraActual.setText(hora);                                                         
         }
       });
        verifyTrain("302");
        t.start();
        Iniciar();
        start();
    }
    
    
    protected void Iniciar(){
        Lector.addDataListener(new DPFPDataAdapter() 
        {
            @Override public void dataAcquired(final DPFPDataEvent e) 
            {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                    //EnviarTexto("La Huella Digital ha sido Capturada");
                    ProcesarCaptura(e.getSample());
            }});}
        });

        Lector.addReaderStatusListener(new DPFPReaderStatusAdapter() 
        {
            @Override public void readerConnected(final DPFPReaderStatusEvent e) 
            {
                SwingUtilities.invokeLater(new Runnable() {	public void run() {
                //EnviarTexto("El Sensor de Huella Digital esta Activado o Conectado");
                }});}
                @Override public void readerDisconnected(final DPFPReaderStatusEvent e) {
                SwingUtilities.invokeLater(new Runnable() {	public void run() {
                EnviarTexto("El Sensor de Huella Digital esta Desactivado o no Conectado");
            }});}
        });

        Lector.addSensorListener(new DPFPSensorAdapter() 
        {
            @Override public void fingerTouched(final DPFPSensorEvent e) 
            {
                SwingUtilities.invokeLater(new Runnable() {	public void run() 
                {
                    EnviarTexto("El dedo ha sido colocado sobre el Lector de Huella");
                }});}
                @Override public void fingerGone(final DPFPSensorEvent e)
                {
                    SwingUtilities.invokeLater(new Runnable() {	public void run() 
                    {
                    //EnviarTexto("El dedo ha sido quitado del Lector de Huella");
                    }});
                }
        });

        Lector.addErrorListener(new DPFPErrorAdapter()
        {
            public void errorReader(final DPFPErrorEvent e)
            {
               SwingUtilities.invokeLater(new Runnable() {  public void run() {
               EnviarTexto("Error: "+e.getError());
               }});
            }
        });
    }
    
  public DPFPFeatureSet featuresinscripcion;
  public DPFPFeatureSet featuresverificacion;
  public  void ProcesarCaptura(DPFPSample sample)
  {
    // Procesar la muestra de la huella y crear un conjunto de características con el propósito de inscripción.
    featuresinscripcion = extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);

    // Procesar la muestra de la huella y crear un conjunto de características con el propósito de verificacion.
    featuresverificacion = extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);

    // Comprobar la calidad de la muestra de la huella y lo añade a su reclutador si es bueno
    if (featuresinscripcion != null)
        try
        {
            System.out.println("Las Caracteristicas de la Huella han sido creada");
            Reclutador.addFeatures(featuresinscripcion);// Agregar las caracteristicas de la huella a la plantilla a crear
            // Dibuja la huella dactilar capturada.
            Image image=CrearImagenHuella(sample);
            DibujarHuella(image);
            identificarHuella();
            Reclutador.clear();
            stop();
            start();
        }catch (DPFPImageQualityException ex) 
        {
            System.err.println("Error");
        }
       }
  public  DPFPFeatureSet extraerCaracteristicas(DPFPSample sample, DPFPDataPurpose purpose)
  {
        DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
        try 
        {
         return extractor.createFeatureSet(sample, purpose);
        }catch (DPFPImageQualityException e) 
        {
         return null;
        }
    }
  public  Image CrearImagenHuella(DPFPSample sample) 
  {
	return DPFPGlobal.getSampleConversionFactory().createImage(sample);
   }

   public void DibujarHuella(Image image) {
          visFicha.lblImagenHuella.setIcon(new ImageIcon(
          image.getScaledInstance(visFicha.lblImagenHuella.getWidth(), visFicha.lblImagenHuella.getHeight(), Image.SCALE_DEFAULT)));
         // repaint();
   }
   public void setTemplate(DPFPTemplate template) 
   {
          DPFPTemplate old = this.template;
          this.template = template;
          //firePropertyChange(TEMPLATE_PROPERTY, old, template);
   }
    public void EnviarTexto(String string) 
    {
          visFicha.txtNotificaciones.setText(string + "\n");
    }
  
    public  void stop()
    {
      Lector.stopCapture();
    }
    public DPFPTemplate getTemplate() {
        return template;
    }
    public  void start()
    {
	Lector.startCapture();
	//EnviarTexto("Utilizando el Lector de Huella Dactilar ");
    }
    
    
  public void identificarHuella(){
     try {
       Connection c=con.getConexion();
       PreparedStatement identificarStmt = c.prepareStatement("SELECT nom_per,ape_per,huella_per,id_per FROM persona ");
       ResultSet rs = identificarStmt.executeQuery();

       while(rs.next()){
            byte temp[] = rs.getBytes("huella_per");
            if (temp!=null) {                          
                Blob templateBuffer = rs.getBlob("huella_per");
                String nombre=rs.getString("nom_per");
                String apellido = rs.getString("ape_per");
                System.out.println(nombre);
                String idPer=rs.getString("id_per");

                DPFPTemplate referenceTemplate = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer.getBytes(1,temp.length));
                setTemplate(referenceTemplate);
                DPFPVerificationResult result = Verificador.verify(featuresverificacion, getTemplate());

                if (result.isVerified()){       //
                    Date fecha = new Date(); //aqui tu fecha;
                    SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
                    String fechaa=dt.format(fecha);
                    dt = new SimpleDateFormat("hh:mm:ss");
                    String hora=dt.format(fecha);
                    String fechaIniFin [] = verifyTrain(idPer); 
                    visFicha.tblDatosHuella.setValueAt( fechaa + "\n" + hora ,0 ,0);                                       
                    visFicha.tblDatosHuella.setValueAt( nombre ,0 ,1);
                    visFicha.tblDatosHuella.setValueAt(apellido, 0, 2);
                    visFicha.tblDatosHuella.setValueAt(fechaIniFin[0]+" al "+fechaIniFin[1], 0, 3);
                    if (Calculos.dateGreaterThanCurrent(fechaIniFin[1]))
                    { 
                        visFicha.tblDatosHuella.setValueAt("ACTIVO", 0, 4);
                        if (tPort.isRunning()) {
                            tPort.stop();
                            tmp=0;
                            openDoor();
                            visFicha.lblPuertaEstado.setText("ABIERTA");
                            countSeconds();
                        }
                        else
                        {
                            openDoor();
                            visFicha.lblPuertaEstado.setText("ABIERTA");
                            countSeconds();
                        }
                    }
                    else
                    {
                        visFicha.tblDatosHuella.setValueAt("NO ACTIVO", 0, 4);
                        if (tPort.isRunning()) {
                            tPort.stop();
                            tmp=0;
                            closeDoor();
                            visFicha.lblPuertaEstado.setText("CERRADA");
                        }
                        else
                        {
                         closeDoor();
                         visFicha.lblPuertaEstado.setText("CERRADA");
                        }
                    }
                   // abrir();
                return;
                }
            }
       }
       visFicha.txtNotificaciones.setText("No existe un registro que coincida con la huella actual\n"
               + "Repita la operacion, revise que su huella no este maltratada");
       visFicha.tblDatosHuella.setValueAt( "---" ,0 ,0);
       visFicha.tblDatosHuella.setValueAt( "---" ,0 ,1);
       visFicha.tblDatosHuella.setValueAt( "---" ,0 ,2);
       visFicha.tblDatosHuella.setValueAt( "---" ,0 ,3);
      
       setTemplate(null);
       } catch (SQLException e) {
           e.printStackTrace();      
       }finally{
       con.desconectar();
       }
   }
  
  public void openDoor()
  {
      PrtoSerial.sendData(1);
      visFicha.lblPuertaEstado.setText("ABIERTA");
      
      
      
  }
  public void closeDoor()
  {
    PrtoSerial.sendData(0);
    visFicha.lblPuertaEstado.setText("CERRADA");
  }
  
  
  int tmp ;
  public void countSeconds()
  {
       
      tPort = new javax.swing.Timer(1000, new ActionListener() 
      {
        
        public void actionPerformed(ActionEvent e) 
        {
            tmp = tmp + 1 ;
           
            System.out.println("counting " +tmp);
            
            if (tmp == 5) {
                System.out.println("count 5 ");
                tPort.stop();
                tmp = 0;
          
            closeDoor();          
            }    
           
        }
        
       });   
        tPort.start();
  }
  
  public void abrir()
  {
        try {
            
            
            TimerTask repeatedTask = new TimerTask() {
                
                public void run() {
                    System.out.println("Task performed on " + new Date());
                    visFicha.btnAbrir.setForeground(Color.green);
                    visFicha.btnAbrir.setBackground(Color.black);
                }
                
            };
           
            Timer timer = new Timer("Timer");
            
            long delay  = 1000L;
            long period = 1000L;
            timer.scheduleAtFixedRate(repeatedTask, delay, period);
            Thread.sleep(2000L);
            timer.cancel();
            visFicha.btnAbrir.setForeground(Color.black);
            visFicha.btnAbrir.setBackground(Color.LIGHT_GRAY);
        } catch (InterruptedException ex) {
            Logger.getLogger(CtrlRegistroEntrada.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
  
  public void cerrar()
  {
      Lector.stopCapture();
  }
  
  public String[] verifyTrain(String idPer)
  {
     String fechaIniFinUltEnt[]=new String[2];
  
     try {           
            ConsBuscarVentas consBuscarVentas = new ConsBuscarVentas();
            ResultSet listFicha = consBuscarVentas.buscarUltimoEntrenPersona(idPer);
                       
            while (listFicha.next()) {
                try {
                   
                    fechaIniFinUltEnt[0] = listFicha.getString("fechaini_hisperser");
                    fechaIniFinUltEnt[1] = listFicha.getString("fechafin_hisperser");
                                                           
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            consBuscarVentas.closeConection();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlFacturaCab.class.getName()).log(Level.SEVERE, null, ex);
        }            
    con.desconectar();
    
     return fechaIniFinUltEnt;
  }

    
    @Override    
    public void actionPerformed(ActionEvent e) 
    {
        
        if (e.getSource() == visFicha.btnAbrir) 
        {
            System.out.println("abrir");
            if (tPort.isRunning()) {
                tPort.stop();
                tmp=0;
                PrtoSerial.sendData(1);
                visFicha.lblPuertaEstado.setText("ABIERTA");
            }
            else{
                PrtoSerial.sendData(1);
                visFicha.lblPuertaEstado.setText("ABIERTA");
            }

            
        }

        if (e.getSource() == visFicha.btnCerrar) 
        {
            System.out.println("cerrar");
             if (tPort.isRunning()) {
                tPort.stop();
                tmp=0;
                PrtoSerial.sendData(0);
                visFicha.lblPuertaEstado.setText("CERRADA");
             }
             else{
                 PrtoSerial.sendData(0);
                 visFicha.lblPuertaEstado.setText("CERRADA");
             }
        }
    }
}
