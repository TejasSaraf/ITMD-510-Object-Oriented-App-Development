/**
 * 
 */
/**
 * 
 */
module Lab4 {
	requires transitive java.sql;       // Makes java.sql accessible to other modules that rely on this module
    requires java.desktop;              // Needed for Swing components (JFrame, JTable, etc.)

    exports models;
    exports controllers;
    exports views;
    exports records;
}