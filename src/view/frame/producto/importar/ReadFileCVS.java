package view.frame.producto.importar;

import model.Producto;
import view.frame.producto.AdministracionProducto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadFileCVS {
    //private List<ColumnSelected> nameColumns;
    private String nameColumns[];
    private BufferedReader br = null;
    private List<RowProduct> listProd;
    private boolean alive = false;
    private int amountProd = 0;
    private int currentCount = 0;
    private List<String> msgError;
    public ReadFileCVS(File file){
        msgError = new ArrayList<>();
        readColumn(file);
    }

    private synchronized void readColumn(File archivo){
        FileReader fr = null;
        //nameColumns = new ArrayList<>();
        listProd = new ArrayList<>();
        try {

            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

            String linea;
            if((linea=br.readLine())!=null) {
                //System.out.println(linea);
                nameColumns = linea.split(";");
                //System.out.println(nameColumns.length);
            }
            fillData();
        }
        catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if( fr != null ){
                    fr.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
    }
    
    private synchronized void fillData(){
        String linea;
        alive = true;
        amountProd = 0;
        try {
            while((linea=br.readLine())!=null) {
                String row[] = linea.split(";");
                listProd.add(new RowProduct(row));
            }
        }catch (IOException exc){
            System.out.println(exc);
        }
        finally {
            try {
                amountProd = listProd.size();
                br.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        alive = false;
    }

    public void getData(int codigo, int codigoBarra,
                        int nombre, int unidad, int disponible,
                        int costo, int precio1, int precio2, int precio3,
                        int incluyeImpuesto, int stockCritico,
                        int requiereStock, int cantidadDisponible){
        msgError.clear();
        currentCount = 0;
        alive = true;
        /*if(codigo!=-1)
            System.out.println("codigo :"+codigo+" "+nameColumns[codigo]);
        if(codigoBarra!=-1)
            System.out.println("codigoBarra :"+codigoBarra+" "+nameColumns[codigoBarra]);
        if(nombre!=-1)
            System.out.println("nombre :"+nombre+" "+nameColumns[nombre]);
        if(unidad!=-1)
            System.out.println("unidad :"+unidad+" "+nameColumns[unidad]);
        if(disponible!=-1)
            System.out.println("disponible :"+disponible+" "+nameColumns[disponible]);
        if(costo!=-1)
            System.out.println("costo :"+costo+" "+nameColumns[costo]);
        if(precio1!=-1)
            System.out.println("precio1 :"+precio1+" "+nameColumns[precio1]);
        if(precio2!=-1)
            System.out.println("precio2 :"+precio2+" "+nameColumns[precio2]);
        if(precio3!=-1)
            System.out.println("precio3 :"+precio3+" "+nameColumns[precio3]);
        if(incluyeImpuesto!=-1)
            System.out.println("incluyeImpuesto :"+incluyeImpuesto+" "+nameColumns[incluyeImpuesto]);
        if(stockCritico!=-1)
            System.out.println("stockCritico :"+stockCritico+" "+nameColumns[stockCritico]);
        if(requiereStock!=-1)
            System.out.println("requiereStock :"+requiereStock+" "+nameColumns[requiereStock]);
        if(cantidadDisponible!=-1)
            System.out.println("cantidadDisponible :"+cantidadDisponible+" "+nameColumns[cantidadDisponible]);*/

        int l = listProd.size();
        AdministracionProducto admin = new AdministracionProducto();
        int countOk  = 0;
        int countErr = 0;
        int countWarning = 0;
        boolean err = false;
        String nombre0;
        String codigo0;
        cont:for (int i = 0;i < l;i++){
            currentCount ++;
            nombre0 = null;
            codigo0 = null;
            err = false;
            RowProduct rp = listProd.get(i);
            String row[] = rp.row;
            Producto prod = new Producto();
            if(codigo!=-1) {
                codigo0 = row[codigo];
                if(codigo0==null || codigo0.isEmpty()){
                    countErr++;
                    msgError.add("Error no se pudo obtener el codigo");
                    continue cont;
                }else {
                    prod.setCodigo(codigo0);
                }

                //System.out.print"Codigo: " + codigo0);
            }

            if(codigoBarra!=-1) {
                prod.setCodigoBarra(row[codigoBarra]);
                //System.out.print", codigoBarra:" + row[codigoBarra]);
            }

            if(nombre!=-1) {
                nombre0  = row[nombre];
                if(nombre0==null || nombre0.isEmpty()){
                    countErr++;
                    msgError.add("Error no se pudo obtener el nombre del producto.");
                    continue cont;
                }else {
                    prod.setNombre(nombre0);
                }
                //System.out.print", nombre:" + nombre0);
            }

            if(unidad!=-1) {
                prod.setUnidadMedida(row[unidad]);
                //System.out.print", unidad:" + row[unidad]);
            }

            if(disponible>-1) {
                Boolean disp = stringToBoolean(row[disponible]);
                if(disp==null){
                    err = true;
                    disp = false;
                    msgError.add("No se pudo reconocer el valor disponible: "+row[disponible]+" ("+codigo0+" "+nombre0+")");
                }
                prod.setDisponible(disp);
                //System.out.print", disponible:" + disp);
            }else if(disponible==-2 || disponible == -3) {
                boolean disp = disponible==-2;
                prod.setDisponible(disp);
                //System.out.print", disponible:" + disp);
            }

            if(costo!=-1) {
                Double v = stringToDouble(row[costo]);
                if(v == null){
                    err = true;
                    v = 0.0;
                    msgError.add("No se pudo reconocer el valor costo: "+row[costo]+" ("+codigo0+" "+nombre0+")");
                }
                prod.setPrecioCosto(v);

                //System.out.print", costo" + v);
            }

            if(precio1!=-1) {
                Double v = stringToDouble(row[precio1]);
                if (v == null) {
                    err = true;
                    v = 0.0;
                    msgError.add("No se pudo reconocer el valor precio1: " + row[precio1] + " ("+codigo0+" "+nombre0+")");
                }
                prod.setPrecio1(v);
                //System.out.print", precio1" + v);
            }
            if(precio2!=-1){
                Double v = stringToDouble(row[precio2]);
                if (v == null) {
                    err = true;
                    v = 0.0;
                    msgError.add("No se pudo reconocer el valor precio2: " + row[precio2] + " ("+codigo0+" "+nombre0+")");
                }
                prod.setPrecio2(v);
                //System.out.print", precio2" + v);
            }
            if(precio3!=-1){
                Double v = stringToDouble(row[precio3]);
                if (v == null) {
                    err = true;
                    v = 0.0;
                    msgError.add("No se pudo reconocer el valor precio3: " + row[precio3] + " ("+codigo0+" "+nombre0+")");
                }
                prod.setPrecio3(v);
                //System.out.print", precio1" +v);
            }
            if(incluyeImpuesto>-1){
                Boolean v = stringToBoolean(row[incluyeImpuesto]);
                if (v == null) {
                    err = true;
                    v = false;
                    msgError.add("No se pudo reconocer el valor incluyeImpuesto: " + row[incluyeImpuesto] + " ("+codigo0+" "+nombre0+")");
                }
                prod.setPrecioIncluyeImpuesto(v);
                //System.out.print", incluyeImpuesto" + v);
            }else if(incluyeImpuesto==-2 || incluyeImpuesto== -3) {
                boolean disp = incluyeImpuesto==-2;
                prod.setPrecioIncluyeImpuesto(disp);
                //System.out.print", incluyeImpuesto" + disp);
            }

            if(stockCritico!=-1){
                Integer v = stringToInt(row[stockCritico]);
                if (v == null) {
                    err = true;
                    v = 0;
                    msgError.add("No se pudo reconocer el valor stockCritico: " + row[stockCritico] + " ("+codigo0+" "+nombre0+")");
                }
                prod.setStockCritico(v);
                //System.out.print", stockCritico" + v);
            }
            if(requiereStock>-1){
                Boolean v = stringToBoolean(row[requiereStock]);
                if (v == null) {
                    err = true;
                    v = false;
                    msgError.add("No se pudo reconocer el valor requiereStock: " + row[requiereStock] + " ("+codigo0+" "+nombre0+")");
                }
                prod.setNoRequiereStock(v);
                //System.out.print", requiereStock" + v);
            }else if(requiereStock==-2 || requiereStock== -3) {
                boolean disp = requiereStock==-2;
                prod.setNoRequiereStock(disp);
                //System.out.print", requiereStock" + disp);
            }

            if(cantidadDisponible!=-1) {
                Integer v = stringToInt(row[cantidadDisponible]);
                if (v == null) {
                    err = true;
                    v = 0;
                    msgError.add("No se pudo reconocer el valor cantidadDisponible: " + row[cantidadDisponible] + " ("+codigo0+" "+nombre0+")");
                }
                prod.setStock(v);
                //System.out.print", cantidadDisponible" + row[cantidadDisponible]);
            }

            boolean rtn = admin.guardar(prod);
            if(!rtn){
                countErr ++;
                msgError.add("Error al intentar guardar el producto "+ codigo0 + " " + nombre0+":"+admin.getMensaje());
            }else if(err){
                countWarning++;
            }else {
                countOk++;
            }
            //System.out.println();
        }
        alive = false;

        System.out.println("Registro: "+currentCount);
        System.out.println("Registro OK: "+countOk);
        System.out.println("Registro Warning: "+countWarning);
        System.out.println("Registro Error: "+countErr);

        for(String msg:msgError){
            System.out.println(msg);
        }
    }

    private Boolean stringToBoolean(String value){
        Boolean rtn = null;

        if(value!=null && value.length() > 0){
            if(value.equalsIgnoreCase("true") ||value.equalsIgnoreCase("false")) {
                rtn = value.equalsIgnoreCase("true");
            }
            else if(value.equalsIgnoreCase("0") ||value.equalsIgnoreCase("1")) {
                rtn = value.equalsIgnoreCase("1");
            }
        }

        return rtn;
    }

    private Double stringToDouble(String value){
        Double rtn = null;

        if(value!=null && value.length() > 0){

            try {
                rtn = Double.parseDouble(value);
            }catch (NumberFormatException exc){}
        }
        return rtn;
    }

    private Integer stringToInt(String value){
        Integer rtn = null;

        if(value!=null && value.length() > 0){
            if(value.indexOf(".")!=-1){
                Double d = Double.parseDouble(value);
                int a = (int)Math.round(d);
                value = String.valueOf(a);
            }
            try {
                rtn = Integer.parseInt(value);
            }catch (NumberFormatException exc){}
        }
        return rtn;
    }

    public String[] getNameColumns() {
        return nameColumns;
    }

    private class RowProduct{
        private String row[];
        public RowProduct(String row[]){
            this.row = row;
        }

        public String getItem(int index){
            return row[index];
        }
    }

    public int getAmountProduct(){
        return amountProd;
    }
    public int getCurrentCount(){
        return currentCount;
    }

    public boolean isAlive(){
        return alive;
    }

    public void runExport(){
        alive = true;
    }
    public void stopExport(){
        alive = false;
    }
}
