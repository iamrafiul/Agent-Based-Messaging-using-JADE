# Agent-Based-Messaging-using-JADE

Jade is an open source platform for peer-to-peer agent based applications. You can get more details about JADE [here](http://jade.tilab.com/documentation/tutorials-guides/).

This is an agent-based messaging system using JADE. You can create multiple agents which can communicate with each other via message.

#Project Setup

####Eclipse(tested in Eclipse Neon.1a Release(4.6.1))

To run the project in eclipse, import it with the option "Import..." and go to Run -> Run Configuration and select "Java Application" from the left menubar and you will be in the "Menu" tab. Go to "Classpath" tab, click on "User Entries". Then select "Add JARs..." from the right panel and add the jade.jar file which is in the root directory of the project(If you don't find it there, find it in build/ directory). Come back to the "Menu" tab and search "Boot" for the Main.Class. 

JADE is actually run from command line, even if you want to run the GUI, you have to run the GUI from command line so for the IDE, you have to give arguments. To do that, go to the "Arguments" tab and write the as following: 

```
-gui -agents "A1:agent.MesAgent"
```

It will create an agent with name A1. As this is a messaging application so creating only one agent doesn't make sense. To create multiple agents set the argument as something like this:

```
-gui -agents "A1:agent.MesAgent; A2:agent.MesAgent; A3:agent.MesAgent"
```

And you are DONE.

Run the project and it will create 3 agents names A1, A2 and A3. For A1, you will see A2 and A3 in the "Recievers" dropdown and same for other twos. You can select any of them and can send message as well as check if the message is sending correctly or not. If any other agent sends you message, you will see them as well.

Enjoy!! :) 


####IntelliJIdea(tested in IntelliJ IDEA 2016.2.4, Build #IU-162.2032.8, built on September 9, 2016)

To run the project in IntelliJIdea, import it with the option "Import from existing source" and go to Run -> Edit Configuration and select "Application" from the left menubar and you will be in the "Configuration" tab. From that tab select "Boot (jade.jar)" as the "Main Class", select your JRE.

JADE is actually run from command line, even if you want to run the GUI, you have to run the GUI from command line so for the IDE, you have to give arguments. To do that, go to the "Arguments" tab and write the as following: 

```
-gui -agents "A1:agent.MesAgent"
```

Run the project and it will create an agent with name A1. As this is a messaging application so creating only one agent doesn't make sense. To create multiple agents set the argument as something like this:

```
-gui -agents "A1:agent.MesAgent; A2:agent.MesAgent; A3:agent.MesAgent"
```

And you are DONE. 

It will create 3 agents names A1, A2 and A3. For A1, you will see A2 and A3 in the "Recievers" dropdown and same for other twos. You can select any of them and can send message as well as check if the message is sending correctly or not. If any other agent sends you message, you will see them as well.

Enjoy!! :) 


#Things to remind.
There is nothing to remind actually. This code has no copyright(It sucks big time!), you can use any part of it as a pet/course project. Feel free to share.

Regards;

Rafiul Sabbir, Skelleftea, Sweden.
