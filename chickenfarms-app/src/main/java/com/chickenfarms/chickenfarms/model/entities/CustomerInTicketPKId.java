package com.chickenfarms.chickenfarms.model.entities;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class CustomerInTicketPKId implements Serializable {
    
    private long ticketId;
    
    private long customerId;
    
    
    @Override
    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        if(object==null){
            return false;
        }
        if(object instanceof CustomerInTicketPKId){
            CustomerInTicketPKId customerInTicketPKId= (CustomerInTicketPKId)object;
            if(customerInTicketPKId.getTicketId()==this.getTicketId() && customerInTicketPKId.getCustomerId()==this.getCustomerId()){
                return true;
            }
        }
        return false;
    }
    
    @Override 
    public int hashCode(){
        final int prime=31;
        int result=1;
        result= (int) (prime*result+ticketId);
        result=prime*result+(int)customerId;
        return result;
    }
    
//    @ManyToOne
//    private Customer customer;

//    @Id
////    @Column(name = "ticket_id")
//    @Column(name = "ticket_id", unique = true,nullable = false)
//    private long ticketId;
//    @Id
////    @ManyToOne
//    @JoinColumn(name="ticket_id_fk", referencedColumnName = "id")
//    private long ticketId;
    
//    @Id
//    @Column(name = "ticket_id", unique = true,nullable = false)
//    private long ticketId;
//
//    @Id
//    @Column(name = "customerId")
//    private int customerId;
//    
  //  @ManyToOne
  //  private Ticket ticket;
}
