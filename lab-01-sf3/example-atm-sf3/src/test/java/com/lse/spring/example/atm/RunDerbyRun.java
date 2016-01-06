package com.lse.spring.example.atm;

import org.apache.derby.client.am.ConnectionCallbackInterface;
import org.apache.derby.drda.NetworkServerControl;

import java.io.*;
import java.net.URL;
import java.sql.*;

/*
 * Simple class to start derby
 */
public class RunDerbyRun {
    static NetworkServerControl derbyControl;
    static boolean derbyStarted = false;
    public static int port = 1527;

    public static void main(String[] args) {
        try {
            startDatabase();

            while (running()) {
                Thread.sleep(10000L);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            System.out.println("DBSetup exiting !");
        }
    }

    public static void startDatabase() {
        if (derbyStarted) {
            return;
        }
        try {
            setupExitHook();

            System.setProperty("derby.system.home", "logs/derby-data");
            System.setProperty("derby.drda.host", "localhost");
            System.setProperty("derby.drda.portNumber", String.valueOf(port));
            System.setProperty("derby.drda.maxThreads", "10");
            //			System.setProperty("derby.drda.logConnections","true");

            derbyControl = new NetworkServerControl();
            derbyControl.start(new PrintWriter(System.out));
            Thread.sleep(5000L);
            derbyStarted = true;
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            System.out.println("derby jvm instance running...");
        }
    }

    private static void setupExitHook() {
        final Thread mainThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                shutdownDatabase();
            }
        });
    }

    public static void shutdownDatabase() {
        try {
            DriverManager.getConnection("jdbc:derby://localhost:"+port+"/test;shutdown=true");
            Thread.sleep(5000L);
            derbyStarted = false;
        } catch (Throwable t) {
//					t.printStackTrace();
        }
    }

    private static boolean running() {
        boolean alive = false;
        if (derbyControl != null) {
            try {
                derbyControl.ping();
                alive = true;
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        return alive;
    }

    public static boolean setupSchema() {
        boolean loadSchema = true;
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:derby://localhost:" + port + "/test;create=true;","test","test");
            connection.setAutoCommit(true);
            Statement statement = connection.createStatement();
            ResultSet rs = null;

            try {
                rs = statement.executeQuery("SELECT 1 FROM DUAL");
            }
            catch (Throwable t) {
                System.out.println(t.getMessage());
            }

            if (rs != null && rs.next()) {
                String value = rs.getString(1);
                if (value != null && "1".equals(value)) {
                    loadSchema = false;
                }
                rs.close();
            }
            if (loadSchema) {
                runSql(connection, "db/derby/createSchema.sql");
                runSql(connection, "db/derby/createTables.sql");
                runSql(connection, "db/derby/loadTables.sql");
            }
            statement.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Throwable ignore) {
                    System.out.println(ignore.getMessage());
                }
            }
        }
        return loadSchema;
    }

    public static int runSql(Connection  connection, String sqlResourceName) {
        int rows = 0;
        try {
            byte[] buffer = fetchResourceAsBytes("/"+sqlResourceName);
            if (buffer!=null) {
                ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                rows = org.apache.derby.tools.ij.runScript(connection, bais, "UTF-8", baos, "UTF-8" );
                System.out.println("runSql("+sqlResourceName+") :=> "+baos.toString());
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return rows;
    }

    private static URL fetchResourceAsURL(final String resourceName) {
        URL url = ClassLoader.getSystemResource(resourceName);

        if (url == null) {
            url = Thread.currentThread().getContextClassLoader().getResource(resourceName);
            if (url == null) {
                url = new Object().getClass().getResource(resourceName);
            }
        }
        if (url == null) {
            System.err.println("could not find resourceName=" + resourceName);
        }
        return url;
    }

    private static byte[] fetchResourceAsBytes(final String resourceName) {
        byte[] resultBytes = null;

        InputStream input = null;

        try {
            int bufferSize = 8192;
            URL url = fetchResourceAsURL(resourceName);

            if (url != null) {
                input = url.openStream();

                BufferedInputStream bis = new BufferedInputStream(input);
                ByteArrayOutputStream baos = new ByteArrayOutputStream(bufferSize);

                int read = 0;
                byte[] buffer = new byte[bufferSize];

                while (read > -1) {
                    read = bis.read(buffer);

                    if (read > 0) {
                        baos.write(buffer, 0, read);
                    }
                }

                bis.close();
                baos.flush();
                baos.close();
                resultBytes = baos.toByteArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (Exception ignore) {
                    System.out.println(ignore.getMessage());
                }
            }
        }

        return resultBytes;
    }
}

