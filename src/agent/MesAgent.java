package agent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
/*These imports are needed to Query AMS for all active agents*/
import jade.domain.AMSService;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;



public class MesAgent extends GuiAgent {
	private MesAgentGui messageGUI;
	private String receiver = "";
	private String content = "";
	private String messagePerformative="";
	private String filename = "messageHistory.txt";
	private String fullConversationText = ""; // all the conversations will be appended here
	public	ArrayList<String> agentList;
	public static int agentCounterInitial = 0;
	public static int agentCounterFinal = 0;

	protected void setup() {
		// Printout a welcome message
		System.out.println("Messenger agent "+getAID().getName()+" is ready.");

		/*This part will query the AMS to get list of all active agents in all containers*/
		agentList	=	new ArrayList();
		refreshActiveAgents();

		// Create and show the GUI
		messageGUI = new MesAgentGui(this);
		messageGUI.displayGUI();


		/** This piece of code, to register services with the DF, is explained
		 * in the book in section 4.4.2.1, page 73
		 **/
		// Register the book-selling service in the yellow pages
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("messenger-agent");
		sd.setName(getLocalName()+"-Messenger agent");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
		addBehaviour(new ReceiveMessage());
	}

	
	//Agent clean-up
	protected void takeDown() {
		// Dispose the GUI if it is there
		if (messageGUI != null) {
			messageGUI.dispose();
		}

		// Printout a dismissal message
		System.out.println("Agent "+getAID().getName()+" is terminating.");

		/** This piece of code, to de-register with the DF, is explained
		 * in the book in section 4.4.2.1, page 73
		 **/
		
		// De-register from the yellow pages
		try {
			DFService.deregister(this);
			System.out.println("Agent "+getAID().getName()+" has been signed off.");
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}

	//Sending message is an implementation of OneShotBehavior(Send once for one time) 
	public class SendMessage extends OneShotBehaviour {
		
		// Send message from to someone 
		public void action() {
			ACLMessage msg;
			if(messagePerformative.equals("Propose")){
				msg = new ACLMessage(ACLMessage.PROPOSE);
			}else{
				msg = new ACLMessage(ACLMessage.REQUEST);
			}

			//the receiver variable is set in getFromGUI() method
			msg.addReceiver(new AID(receiver, AID.ISLOCALNAME));
			msg.setLanguage("English");
			msg.setContent(content);
			send(msg);
			
			//saveToFile(getAID().getLocalName() +":"+ content);
			fullConversationText += "\nMe: "+msg.getContent();
			messageGUI.setMessageTextArea(fullConversationText);

			System.out.println(getAID().getName()+" sent a message to "+receiver+"\n"+
					"Content of the message is: "+ msg.getContent());
		}
	}
	
	//Receiving message is an implementation of CyclicBehavior(Receive until takeDown() is executed)
	
	public class ReceiveMessage extends CyclicBehaviour {

		// Variable for the contents of the received Message
		private String messagePerformative;
		private String messageContent;
		private String SenderName;
		private String MyPlan;

		// Receive message and append it in the conversation textArea in the GUI  
		public void action() {
			ACLMessage msg = receive();
			if(msg != null) {

				messagePerformative = msg.getPerformative(msg.getPerformative());
				messageContent = msg.getContent();
				SenderName = msg.getSender().getLocalName();
				
				// print the message details in console
				System.out.println("**** " + getAID().getLocalName() + " received a aessage" +"\n"+
						"Sender name: "+ SenderName+"\n"+
						"Content of the message: " + messageContent + "\n"+
						"Message type: " + messagePerformative + "\n");
				System.out.println("**********************************");
				
				fullConversationText += "\n"+SenderName+": "+messageContent;
				messageGUI.setMessageTextArea(fullConversationText);

			}

		}
	}
	//get all entered input from gui agent
	public void getFromGui(final String messageType, final String dest, final String messages) {
		addBehaviour(new OneShotBehaviour() {
			public void action() {
				messagePerformative = messageType;
				receiver = dest;
				content = messages;
			}
		} );
	}

	//predefined function of GuiAgent - see postGuiEvent() in MesAgentGui.java
	@Override
	protected void onGuiEvent(GuiEvent arg0) {
		// TODO Auto-generated method stub
		addBehaviour(new SendMessage());

	}

	// if new agents are created after instantiating this object
	// this method will keep the lists updated
	public void refreshActiveAgents(){

		AMSAgentDescription [] agents = null;


	    try {
	        SearchConstraints c = new SearchConstraints();
	        c.setMaxResults ( new Long(-1) );
	        agents = AMSService.search( this, new AMSAgentDescription (), c );
	    }
	    catch (Exception e) {  }

	    // Add all the active agents in the agent list to show in drop-down
	    for (int i=0; i<agents.length;i++){
	        AID agentID = agents[i].getName();
	        if(agentID.getLocalName().equals("ams") || agentID.getLocalName().equals("rma") || agentID.getLocalName().equals("df"))
	        	continue;
	        agentList.add(agentID.getLocalName());
	    }

	}

	public void listActiveAgents(){

		for(String agent: agentList)
			System.out.println("Active:"+agent);

	}

}
