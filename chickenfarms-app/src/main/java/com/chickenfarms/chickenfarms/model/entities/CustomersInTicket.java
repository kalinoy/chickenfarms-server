package com.chickenfarms.chickenfarms.model.entities;

import com.chickenfarms.chickenfarms.model.Customer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CustomersInTicketTable")
@Getter
@Setter
@Builder
//@AssociationOverrides({
//        @AssociationOverride(name = "primaryKey.ticket",
//                joinColumns = @JoinColumn(name = "ticket_id")),
//        @AssociationOverride(name = "primaryKey.customer",
//                joinColumns = @JoinColumn(name = "customer_id")) })
//@AssociationOverrides({
//        @AssociationOverride(name = "primaryKey.ticket",
//                joinColumns = @JoinColumn(name = "ticket_id"))})
//@IdClass(CustomerInTicketPKId.class)
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
    
    
    @Column(name = "addedDate")
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

//    @Id
//////    @Column(name = "ticket_id")
//    @Column(name = "ticket_id", unique = true,nullable = false)
//    private long ticketId;
////    @Id
////    @ManyToOne
////    @JoinColumn(name="ticket_id_fk", referencedColumnName = "id")
////    private long ticketId;
//
//    @Id
//    @Column(name = "customerId")
//    private int customerId;
//
//    @Column(name = "addedDate")
//    private Date addedDate=new Date(System.currentTimeMillis());
    
//    @ManyToOne
//    @JoinColumn(name = "ticket_id")
//////    @ToString.Exclude
////    @JoinColumn(name = "ticket_id", insertable=false, updatable=false)
//    @ManyToOne
//    @JoinColumn(name="ticket_id_fk", referencedColumnName = "ticket_id")
//    private Ticket ticket;
}
