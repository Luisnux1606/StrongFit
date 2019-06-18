/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

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
import modelos.Conexion;
import vistas.VisFicha;

/**
 *
 * @author Administrator
 */
public class CtrlRegistroEntrada {
    
    VisFicha visFicha;
    Conexion con=new Conexion();
    
    //Varible que permite iniciar el dispositivo de lector de huella conectado
    // con sus distintos metodos.
    private DPFPCapture Lector = DPFPGlobal.getCaptureFactory().createCapture();

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
     try{
     System.out.println("Las Caracteristicas de la Huella han sido creada");
     Reclutador.addFeatures(featuresinscripcion);// Agregar las caracteristicas de la huella a la plantilla a crear
     // Dibuja la huella dactilar capturada.
     Image image=CrearImagenHuella(sample);
     DibujarHuella(image);
    identificarHuella();
    Reclutador.clear();
    stop();
    start();
     }catch (DPFPImageQualityException ex) {
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
    
    
 /**
  * Identifica a una persona registrada por medio de su huella digital
  */
  public void identificarHuella(){
     try {
       //Establece los valores para la sentencia SQL
       Connection c=con.getConexion();
       //Obtiene todas las huellas de la bd
       PreparedStatement identificarStmt = c.prepareStatement("SELECT nom_per,huella_per,id_per FROM persona ");
       ResultSet rs = identificarStmt.executeQuery();
       //Si se encuentra el nombre en la base de datos
       while(rs.next()){
       //Lee la plantilla de la base de datos
            byte temp[] = rs.getBytes("huella_per");
            if (temp!=null) {                          
                Blob templateBuffer = rs.getBlob("huella_per");
                String nombre=rs.getString("nom_per");
                System.out.println(nombre);
                String clave=rs.getString("id_per");
                //Crea una nueva plantilla a partir de la guardada en la base de datos
                DPFPTemplate referenceTemplate = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer.getBytes(1,temp.length));
                //Envia la plantilla creada al objeto contendor de Template del componente de huella digital
                setTemplate(referenceTemplate);
                // Compara las caracteriticas de la huella recientemente capturda con la
                // alguna plantilla guardada en la base de datos que coincide con ese tipo
                DPFPVerificationResult result = Verificador.verify(featuresverificacion, getTemplate());
                //compara las plantilas (actual vs bd)
                //Si encuentra correspondencia dibuja el mapa
                //e indica el nombre de la persona que coincidió.
                if (result.isVerified()){       
                    Date fecha = new Date(); //aqui tu fecha;
                    SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
                    String fechaa=dt.format(fecha);
                    visFicha.tblDatosHuella.setValueAt( fechaa ,0 ,0);
                    dt = new SimpleDateFormat("hh:mm:ss");
                    String hora=dt.format(fecha);
                    visFicha.tblDatosHuella.setValueAt( hora ,0 ,1);
                    visFicha.tblDatosHuella.setValueAt( nombre ,0 ,2);
                   // consultaES(clave);       
                    abrir();
                return;
                }
            }
       }
       //Si no encuentra alguna huella correspondiente al nombre lo indica con un mensaje
       visFicha.txtNotificaciones.setText("No existe un registro que coincida con la huella actual\n"
               + "Repita la operacion, revise que su huella no este maltratada");
       visFicha.tblDatosHuella.setValueAt( "---" ,0 ,0);
       visFicha.tblDatosHuella.setValueAt( "---" ,0 ,1);
       visFicha.tblDatosHuella.setValueAt( "---" ,0 ,2);
       visFicha.tblDatosHuella.setValueAt( "---" ,0 ,3);
      
       setTemplate(null);
       } catch (SQLException e) {
           e.printStackTrace();
       //Si ocurre un error lo indica en la consola
       //System.err.println("Error al identificar huella dactilar."+e.getMessage());
       }finally{
       con.desconectar();
       }
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
            Thread.sleep(5000L);
            timer.cancel();
             visFicha.btnAbrir.setForeground(Color.black);
            visFicha.btnAbrir.setBackground(Color.LIGHT_GRAY);
        } catch (InterruptedException ex) {
            Logger.getLogger(CtrlRegistroEntrada.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
  
  public void cerrar()
  {
  
  }
  
  public void consultaES(String clave){
     Connection ch=con.getConexion();
     int hactual,hbase,mactual,mbase;   
     String hora1=null;
     Date dias = new Date(); //consultamos en que dia nos encontramos;
     SimpleDateFormat dtdia=new SimpleDateFormat("EEEE");
     String fechaaa=dtdia.format(dias);
     String diain="e"+fechaaa;//modificamoes esta parte
     String diaout="s"+fechaaa;
     //------------------------------------------------------------
     try {
                PreparedStatement consultah = ch.prepareStatement("SELECT *FROM horario where clave_trabajador=?");
                consultah.setString(1,clave);
                ResultSet res = consultah.executeQuery();
                while (res.next()) {
                //si se encuentran datos lo insertamos en la tabla
                    String edia=res.getString(diain);//extraemos la hora de entrada
                    String sdia=res.getString(diaout);

                    Date hora=new Date();//creamos la hora actual
                    SimpleDateFormat dt = new SimpleDateFormat("HH:mm");
                    String horatabla=edia;
                try {
                    Date htabla = dt.parse(horatabla);
                } catch (ParseException ex) {
                    Logger.getLogger(CtrlRegistroEntrada.class.getName()).log(Level.SEVERE, null, ex);
                }
                    Date htabla;
                try {
                    htabla = dt.parse(horatabla);
                    hora1=dt.format(htabla);
                    //System.err.println(hora1);//conversion a tipo date de la horas de entrada en el horario
                } catch (ParseException ex) {
                    Logger.getLogger(CtrlRegistroEntrada.class.getName()).log(Level.SEVERE, null, ex);
                }
                    String hora2=dt.format(hora);//hora actual de entrada
                    
                    //convertir las horas a entero para compararlas

                    hactual=Integer.parseInt(hora2.substring(0, 2));
                    hbase=Integer.parseInt(hora1.substring(0, 2));

                    mactual=Integer.parseInt(hora2.substring(3, 5));
                    mbase=Integer.parseInt(hora1.substring(3, 5));

                    //System.err.println(hactual+" "+hbase+" minutos "+mactual+" "+mbase);
                    int horaminutos_entrada=(hbase*60)+mbase,horaminutos_actual=(hactual*60)+mactual;//convertimos todo a minutos para que sea facil la comparacion
                    //System.err.println("minutos actual "+horaminutos_actual);
                    //System.err.println("minutos entrada "+horaminutos_entrada);
                    Date fecha=new Date();
                    SimpleDateFormat fech = new SimpleDateFormat("dd/MM/yyyy");//creamos la fecha actual que se guardara en la base de datos
                    String fechaa=fech.format(fecha);

                    if(horaminutos_entrada<horaminutos_actual){//si tiempo de entrada es menor que tiempo actual
                    visFicha.tblDatosHuella.setValueAt( "Entrada",0 ,3);//retardo
                    visFicha.tblDatosHuella.setValueAt( "Retardo" ,0 ,4);
                    visFicha.txtNotificaciones.setText("Asistencia registrada\n"
                            + "Procure llegar mas temprano");
                    PreparedStatement insertar = ch.prepareStatement("insert into historial(clave_trabajador,fecha,estado) values('"+clave+"','"+fechaa+"','Retardo');");
                    insertar.executeQuery();
                     
                    

                                   }
                    else{
                    visFicha.tblDatosHuella.setValueAt( "Entrada",0 ,3);
                    visFicha.tblDatosHuella.setValueAt( "Normal" ,0 ,4);
                    visFicha.txtNotificaciones.setText("Asistencia registrada\n");
                    PreparedStatement insertar = ch.prepareStatement("insert into historial(clave_trabajador,fecha,estado) values('"+clave+"','"+fechaa+"','Normal');");
                    insertar.executeQuery();
                                        }
                     }
            } catch (SQLException ex) {
                PreparedStatement actualiza_asistencia;
            try {
                actualiza_asistencia = ch.prepareStatement("UPDATE personal SET asistencia=true WHERE clave='" + clave + "';");
                actualiza_asistencia.executeQuery();
            } catch (SQLException ex1) {
                //Logger.getLogger(RegEntradaSalida.class.getName()).log(Level.SEVERE, null, ex1);
            }             
            }
     //----------------------------------------------------------------
  con.desconectar();
    }
}
