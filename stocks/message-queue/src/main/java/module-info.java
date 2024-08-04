module messagequeue {
    exports nl.rug.aoop.messagequeue.queues;
    exports nl.rug.aoop.messagequeue.factories;
    exports nl.rug.aoop.messagequeue;
    exports nl.rug.aoop.messagequeue.messages;
    exports nl.rug.aoop.messagequeue.users;
    // Needed for gson to work. If your message queue resides in a sub-package,
    // be sure to open this to com.google.gson as well.
    //    opens nl.rug.aoop.messagequeue to com.google.gson;
    requires static lombok;
    requires org.slf4j;
    requires org.mockito;
    requires com.google.gson;
    requires networking;
    requires command;
}