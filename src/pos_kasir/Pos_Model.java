/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pos_kasir;

/**
 *
 * @author valer
 */
public class Pos_Model {
  
    private double totalharga = 0;

    public double getTotalharga() {
        return totalharga;
    }

    
    public void total(double total){
        totalharga += total;
    }
}
