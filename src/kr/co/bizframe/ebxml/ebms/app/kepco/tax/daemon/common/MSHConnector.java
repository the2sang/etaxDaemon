package kr.co.bizframe.ebxml.ebms.app.kepco.tax.daemon.common;

import java.io.*;
import java.util.ArrayList;
import java.util.Vector;
import kr.co.bizframe.ebxml.ebms.message.handler.MSHException;
import kr.co.bizframe.ebxml.ebms.message.msi.MSHConnection;
import kr.co.bizframe.ebxml.ebms.message.msi.Message;
import kr.co.bizframe.ebxml.ebms.message.msi.Payload; 

public class MSHConnector {

   private String g_host;
   private String g_port;
   private String g_from_party_id;
   private String g_from_party_type;
   private String g_to_party_id;
   private String g_to_party_type;
   private String g_cpaid;
   private String g_service;
   private String g_action;
   private String g_conversationid;


   private Vector g_file_name_list = new Vector();
   private Vector g_file_dir_list  = new Vector();
   private Vector g_file_contents  = new Vector();

   private boolean file_payload_exists   = false;
   private boolean string_payload_exists = false;
   private ArrayList payload = new ArrayList();
   
	public MSHConnector(){}

   public void setHost(String host)                       { this.g_host            = host; }
   public void setPort(String port)                       { this.g_port            = port; }
   public void setFrom_party_id(String from_party_id)     { this.g_from_party_id   = from_party_id; }
   public void setFrom_party_type(String from_party_type) { this.g_from_party_type = from_party_type; }
   public void setTo_party_id(String to_party_id)         { this.g_to_party_id     = to_party_id; }
   public void setTo_party_type(String to_party_type)     { this.g_to_party_type   = to_party_type; }
   public void setCpa_id(String cpaid)                    { this.g_cpaid           = cpaid; }
   public void setConversation_id(String g_conversationid)                  { this.g_conversationid           = g_conversationid; }
   public void setService(String service)                 { this.g_service         = service; }
   public void setAction(String action)                   { this.g_action          = action; }

   public String getHost()                       { return this.g_host; }
   public String getPort()                       { return this.g_port; }
   public String getFrom_party_id()              { return this.g_from_party_id; }
   public String getFrom_party_type()            { return this.g_from_party_type; }
   public String getTo_party_id()                { return this.g_to_party_id; }
   public String getTo_party_type()              { return this.g_to_party_type; }
   public String getCpa_id()                     { return this.g_cpaid; }
   public String getConversation_id()            { return this.g_conversationid; }
   public String getService()                    { return this.g_service; }
   public String getAction()                     { return this.g_action; }

   public String getFile_name(int i) {
      return (String)g_file_name_list.get(i);
   }
   public String getFile_dir(int i) {
      return (String)g_file_dir_list.get(i);
   }

   public void addPayload(String file_name, String file_dir) { 
      g_file_name_list.add(file_name);
      g_file_dir_list.add (file_dir);
      file_payload_exists = true;
   }

   public void addPayload(String contents) {
      g_file_contents.add(contents);
      string_payload_exists = true;
   }

   public int sendAsyncMessage() {
      try{

         MSHConnection conn = new MSHConnection(getHost(), getPort());
/*
         try {
            java.lang.Thread.sleep(1000);
         }catch(Exception e) {
            e.printStackTrace();
         }
*/
         Message msg = new Message();

         msg.setFromPartyId     (getFrom_party_id());
         msg.setFromPartyIdType (getFrom_party_type());
         
         msg.setToPartyId       (getTo_party_id());
         msg.setToPartyIdType   (getTo_party_type());

         msg.setCPAId(getCpa_id());
         msg.setConversationId(getConversation_id());
         msg.setService(getService());
         msg.setAction(getAction());

         if(file_payload_exists) {
            for(int i=0; i<g_file_name_list.size(); i++) {
               String payload_path = getFile_dir(i) + File.separator + getFile_name(i);
               byte[] bb = getPayload(payload_path);
               Payload p = new Payload();
               p.writeBytes(bb);
               msg.addPayload(p);
            }
         }

         if(string_payload_exists) {
            for(int i=0; i<g_file_contents.size(); i++) {
               String payload_contents = (String)g_file_contents.get(i);
               byte[] bb = payload_contents.getBytes();
               Payload p = new Payload();
               p.writeBytes(bb);
               msg.addPayload(p);
            }
         }
         
         conn.sendAsync(msg);
         conn.close();

      }catch(MSHException e){
         // in case fail return not-0
         return e.getCode();
      }
      return 1000;
	}
   
   
   public int sendMessage() {
      try{

         MSHConnection conn = new MSHConnection(getHost(), getPort());
/*
         try {
            java.lang.Thread.sleep(1000);
         }catch(Exception e) {
            e.printStackTrace();
         }
*/
         Message msg = new Message();

         msg.setFromPartyId     (getFrom_party_id());
         msg.setFromPartyIdType (getFrom_party_type());
         
         msg.setToPartyId       (getTo_party_id());
         msg.setToPartyIdType   (getTo_party_type());

         msg.setCPAId(getCpa_id());
         msg.setConversationId(getConversation_id());
         msg.setService(getService());
         msg.setAction(getAction());

//         if(file_payload_exists) {
//            for(int i=0; i<g_file_name_list.size(); i++) {
//               String payload_path = getFile_dir(i) + File.separator + getFile_name(i);
//               byte[] bb = getPayload(payload_path);
//               Payload p = new Payload();
//               p.writeBytes(bb);
//               msg.addPayload(p);
//            }
//         }
//
//         if(string_payload_exists) {
//            for(int i=0; i<g_file_contents.size(); i++) {
//               String payload_contents = (String)g_file_contents.get(i);
//               byte[] bb = payload_contents.getBytes();
//               Payload p = new Payload();
//               p.writeBytes(bb);
//               msg.addPayload(p);
//            }
//         }
         
         for(int i=0; i<payload.size(); i++){
         	byte[] data = (byte[])payload.get(i);
	            Payload p = new Payload();
	            p.writeBytes(data);
	            msg.addPayload(p);
         }
         
         conn.sendAsync(msg);
         conn.close();

      }catch(MSHException e){
         // in case fail return not-0
         return e.getCode();
      }
      return 1000;
	}


	private byte[] getPayload(String fileName){
		
    byte[] result = null;
    try{
       FileInputStream fis        = new FileInputStream(fileName);
       ByteArrayOutputStream baos = new ByteArrayOutputStream();
       int size = 0;
       byte[] tmp = new byte[4096];
       while((size = fis.read(tmp)) != -1){
          baos.write(tmp, 0, size);
       }
       result = baos.toByteArray();	
    }catch(Exception e){
       System.out.println("Error :" + e.toString());
       //e.printStacktrace();
    }
    return result;
	}

	
	 /**
     * @param sendContents
     */
    public void addPayload(byte[] sendContents) {
        payload.add(sendContents);
    }

	

}

