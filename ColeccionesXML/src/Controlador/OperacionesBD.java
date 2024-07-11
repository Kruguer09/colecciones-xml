/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.basex.core.BaseXException;
import org.basex.core.Context;
import org.basex.core.cmd.CreateDB;
import org.basex.core.cmd.XQuery;

/**
 *
 * @author USER
 */
public class OperacionesBD {
    private Context contexto;
    private String directorioBD ="src/Recursos/colecciones.xml";
    //si le ponemos que reciba el string de la consulta tb se puee camiando método y meterle parametro
    
//    private final String QUERY_NUMERO_CURSOS ="for $a in/cursos return count($a/curso)";
//    private final String QUERY_CURSO ="for $a in/cursos/curso return ($a)";//sin parametrizar
//    private final String QUERY_CURSO ="for $a in/cursos return $a/curso[%s]";//parametrizada
    
    
    public String operacion(String consulta){
        if(contexto==null){
            crearBD();
        }
        try{
            String xq= new XQuery(consulta).execute(contexto);
            return xq;
        }catch (BaseXException ex){
            System.out.println("--> La base de datos no ha podido crearse");
            System.out.println(ex.getMessage());
            return "Error";
        }
    }
    public void crearBD(){
        if(contexto==null){
            contexto=new org.basex.core.Context();
            try{
                CreateDB baseDatos=new CreateDB("Cursos",directorioBD);
                baseDatos.execute(contexto);
                
            }catch (BaseXException ex){
                System.out.println("--> La base de datos no ha podido crearse");
                System.out.println(ex.getMessage());
            }
        }
    }
    public String cargaScript(int numScript){
        InputStreamReader in =null;
        try{
            in = new InputStreamReader(getClass().getResourceAsStream("/Script/script"+numScript+".xq"),"UTF-8");
        }catch(UnsupportedEncodingException ex){
            System.out.println("Error abriendo InputStreamReader");
            System.out.println(ex);
        }
        if(in!=null){
            StringBuilder build= new StringBuilder();
            char[] chars =new char[8192];
            int count;
            try{
                while((count=in.read(chars,0,chars.length))!=-1){
                    if(count>0){
                        build.append(chars,0,count);
                    }
                }
            }catch (IOException ex){
                System.out.println("Error de lectura-secritura");
                System.out.println(ex);
            }finally{
                try{
                    in.close();
                }catch(IOException ex){
                    System.out.println("Error cerrando Stream");
                    System.out.println(ex);
                }
            }
            return build.toString();
            
        }else{
            return "Error";
        }
    }
}
