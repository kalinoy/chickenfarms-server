package com.chickenfarms.chickenfarms.model.entity;

import com.chickenfarms.chickenfarms.model.Customer;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CustomersInTicketTable")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomersInTicket {
    
    @EmbeddedId
    private CustomerInTicketPKId pk=new CustomerInTicketPKId();
        
    @ManyToOne
    @MapsId("ticketId")
    @JoinColumn(name = "ticket_ticket_id", nullable = false)
    private Ticket ticket;
    
    @ManyToOne
    @JoinColumn(name = "customer_customer_id", nullable = false)
    @MapsId("customerId")
    private Customer customer;
    
    
    @Column(name = "added_Date")
    private Date addedDate=new Date(System.currentTimeMillis());
    
    @Override
    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        if(object==null){
            return false;
        }
        if(object instanceof CustomersInTicket){
            CustomersInTicket customersInTicket= (CustomersInTicket)object;
            if(customersInTicket.getPk().equals(this.getPk())){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int hashCode(){
        final int prime=31;
        int result=1;
        result= prime*result+((pk==null)? 0 : pk.hashCode());
        return result;
    }
    
}
