/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pos.agencia.rs.entities;

/**
 *
 * @author natarajan
 */
public class ErrorMessage {

    private String description;

    public ErrorMessage() {
    }

    public ErrorMessage(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
