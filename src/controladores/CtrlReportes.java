/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import assets.Validaciones;
import com.toedter.calendar.JDateChooser;
import consultas.ConsPersona;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import modelos.Conexion;
import modelos.Persona;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.*;
import net.sf.jasperreports.view.JasperViewer;

import vistas.VisReportes;

/**
 *
 * @author Administrator
 */

//'11/7/2017'
public class CtrlReportes extends Conexion implements ActionListener{
    
    VisReportes visRepo;
    Connection  conexion; 
    Map parametros=null;
    JasperReport reporteMaestro;    
    String rutaJasper;
    JasperPrint jP;
    ConsPersona consP;
    Persona p;
    
    
    public CtrlReportes(VisReportes visRepo,String rutaJasper)
    {
        this.visRepo = visRepo; 
        this.rutaJasper =rutaJasper;
        this.conexion = getConexion();
        
        consP = new ConsPersona();
        
        parametros=new HashMap();
        visRepo.btnGenerarRepoPersonas.addActionListener(this);
        visRepo.btnGenerarRepoRegistros.addActionListener(this);
        visRepo.btnReportePersonasBuscarPersonas.addActionListener(this);
        iniciar();
    }
    
    public void iniciar()
    {
        visRepo.setLocation(400, 200);      
        visRepo.setSize(600,300);
        visRepo.setVisible(true);
    }
    
    
    public void getUsuario(String pCedPer,Date pFIniFicha)
    {              
        String fIni=Validaciones.setFormatFecha(pFIniFicha);       
        parametros.put("p_id_per", Integer.parseInt(pCedPer));
        
        parametros.put("p_fechaIni_ficha", fIni);
        
        try//RepoAnalisisPersona.jasper
        {
            try 
            {
                try
                {
                    System.out.println(pCedPer + " "+fIni );
                    reporteMaestro = (JasperReport) JRLoader.loadObjectFromFile(rutaJasper+"RepoAnalisisPersona.jasper");
                }
                catch (JRException e) {e.printStackTrace();}
                
                 jP = JasperFillManager.fillReport(reporteMaestro,parametros,conexion);
            }
            catch (JRException e) 
            {
                e.printStackTrace();
            }

            JasperViewer.viewReport( jP, false);


        }catch (Exception e){e.printStackTrace();}
    }	
    
     public void getVentas(Date fI,Date fF)
     {                        
         String fIni=Validaciones.setFormatFecha(fI);
         String fFin=Validaciones.setFormatFecha(fF);
         
         System.out.println(fIni+"   ::   "+fFin);
         parametros.put("p_fechaIni", fIni); 
         parametros.put("p_fechaFin", fFin);

        try
        {
             try
                {
                    reporteMaestro = (JasperReport) JRLoader.loadObjectFromFile(rutaJasper+"RepoFicha.jasper");
                }
                catch (JRException e) {e.printStackTrace();}
                
                 jP = JasperFillManager.fillReport(reporteMaestro,parametros,conexion);

            JasperViewer.viewReport( jP, false);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
     }
    
     public boolean validaRepoFichaPerona(JTextField ced, JDateChooser fechaIni)
     {
         if (Validaciones.isVoidJTxt(ced) 
          && Validaciones.isVoidDateChooser(fechaIni))
         {
             return true;
         }
         else
             return false;
         
         
     }
     public boolean validaRepoFicha(JDateChooser fechaIni, JDateChooser fechaFin)
     {
         if (Validaciones.isVoidDateChooser(fechaIni) 
          && Validaciones.isVoidDateChooser(fechaFin))
         {
             return true;
         }
         else
             return false;
         
         
     }
     
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == visRepo.btnGenerarRepoPersonas) 
        {                   
            
           String cedula = visRepo.txtCedulaReportePersonas.getText();
           Date fechaIniFicha = visRepo.dtcFechaReporte.getDate();
           
         //  if (validaRepoFichaPerona(visRepo.txtCedulaReportePersonas,visRepo.dtcFechaReporte))                             
            getUsuario(cedula,fechaIniFicha);
        }
        
        if (e.getSource() == visRepo.btnGenerarRepoRegistros) 
        {                   
            
           Date  fechaIniFicha= visRepo.dtcFechaInicioReportesRegistros.getDate();
           Date fechaFinFicha  = visRepo.dtcFechaFinReportesRegistros.getDate();
           if (validaRepoFicha(visRepo.dtcFechaInicioReportesRegistros, visRepo.dtcFechaFinReportesRegistros))
            getVentas(fechaIniFicha,fechaFinFicha);
        }
        
        if (e.getSource() == visRepo.btnReportePersonasBuscarPersonas) 
        {                   
            
            CtrlBuscarPersonas ctrBP=new CtrlBuscarPersonas (visRepo);
         
                       
        }
        
        
       
    }
}
