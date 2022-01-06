package com.chickenfarms.chickenfarms.utils;

import com.chickenfarms.chickenfarms.enums.TicketStatus;
import com.chickenfarms.chickenfarms.model.CommentBusinessDetails;
import com.chickenfarms.chickenfarms.model.TagBusinessDetails;
import com.chickenfarms.chickenfarms.model.TicketBusinessDetails;
import com.chickenfarms.chickenfarms.model.entity.Comment;
import com.chickenfarms.chickenfarms.model.entity.Tag;
import com.chickenfarms.chickenfarms.model.entity.Ticket;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BusinessDetailsConverterUtils {
    
    public static Set<TicketBusinessDetails> getTicketsBusinessList(Set<Ticket> ticketsByPage) {
        Set<TicketBusinessDetails> ticketsBusinessDetails=new HashSet<>();
        ticketsByPage.forEach(ticket -> {
            TicketBusinessDetails ticketBusinessDetails = getTicketBusinessDetails(ticket);
            ticketsBusinessDetails.add(ticketBusinessDetails);
        });
        return ticketsBusinessDetails;
    }
    
    public static TicketBusinessDetails getTicketBusinessDetails(Ticket ticket) {
//        List<String> ticketTagsName= ticket.getTags().stream().map(Tag::getTagName).collect(Collectors.toList());
        List<String> ticketTagsName=getTagsName(ticket.getTags());
        TicketBusinessDetails ticketBusinessDetails=TicketBusinessDetails.builder().ticketId(ticket.getTicketId()).description(ticket.getDescription()).farmId(ticket.getFarmId()).status(ticket.getStatus()).tagsName(ticketTagsName).problemId(ticket.getProblem().getProblemId()).userName(ticket.getUser().getUserName()).build();
        setAdditionalTicketBusinessDetails(ticket, ticketBusinessDetails);
        return ticketBusinessDetails;
    }
    
    
    private static List<String> getTagsName(Set<Tag> tags){
        if(tags==null){
            return new ArrayList<>();
        }
        return tags.stream().map(Tag::getTagName).collect(Collectors.toList());
    }
    
    public static List<CommentBusinessDetails> getCommentsBusinessList(List<Comment> comments) {
        List<CommentBusinessDetails> commentsBusinessDetails=new ArrayList<>();
        comments.forEach(comment -> {
            CommentBusinessDetails commentBusinessDetails = getCommentBusinessDetails(comment);
            commentsBusinessDetails.add(commentBusinessDetails);
        });
        return commentsBusinessDetails;
    }
    
    public static CommentBusinessDetails getCommentBusinessDetails(Comment comment) {
        CommentBusinessDetails commentBusinessDetails=new CommentBusinessDetails(comment.getTicket().getTicketId(), comment.getCommentText(), comment.getCreatedDate(), comment.getUser().getUserName());
        return commentBusinessDetails;
    }
    
    
    public static List<TagBusinessDetails> getTagBusinessDetailsList(List<Tag> tags){
        List<TagBusinessDetails> tagsBusinessDetails=new ArrayList<>();
        tags.forEach(tag->{
            TagBusinessDetails tagBusinessDetails=getTagBusinessDetails(tag);
            tagsBusinessDetails.add(tagBusinessDetails);
        });
        return tagsBusinessDetails;
    }
    
    public static TagBusinessDetails getTagBusinessDetails(Tag tag){
        List<Long> ticketTagsName= tag.getTickets().stream().map(Ticket::getTicketId).collect(Collectors.toList());
        return new TagBusinessDetails(tag.getTagName(),ticketTagsName);
    }
    

    
    private static void setAdditionalTicketBusinessDetails(Ticket ticket, TicketBusinessDetails ticketBusinessDetails) {
        if(isClosedOrReadyStatus(ticket)){
            ticketBusinessDetails.setSla(ticket.getSla());
            ticketBusinessDetails.setGrade(ticket.getGrade());
            ticketBusinessDetails.setRootCauseName(ticket.getRootCause().getRootCauseName());
        }
        if(isStatus(ticket, TicketStatus.CLOSED)){
            ticketBusinessDetails.setResolved(ticket.isResolved());
        }
    }
    
    private static boolean isClosedOrReadyStatus(Ticket ticket) {
        return isStatus(ticket, TicketStatus.CLOSED) || isStatus(ticket, TicketStatus.READY);
    }
    
    private static boolean isStatus(Ticket ticket, TicketStatus ticketStatus) {
        return ticket.getStatus().equals(ticketStatus.getTicketStatus());
    }
}
