package bd;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.LinkedList;
import CBAFuzzy.AssocRuleMining.RuleNode;
import java.sql.DriverManager;

public class UpdateData {

	Statement stm;

	public static void updateRules(RuleNode rules, int numRules, LinkedList itemsTranslatorList){
            int ruleNumber = 0;
            RuleNode linkRuleNode = rules;
            LinkedList[] antecedentsAtts, antecedentsVals;
            String[] consequents;
            double[] confidences;

            antecedentsAtts = new LinkedList[numRules];
            antecedentsVals = new LinkedList[numRules];
            consequents = new String[numRules];
            confidences = new double[numRules];
	
            while(linkRuleNode != null) {
                //Output antecedent
		if(linkRuleNode.next!=null){
                    antecedentsAtts[ruleNumber] = new LinkedList();
                    antecedentsVals[ruleNumber] = new LinkedList();
                    short[] itemSet = linkRuleNode.antecedent;
                    for (int index=0;index<itemSet.length;index++){
                        String currentItem = itemsTranslatorList.get(itemSet[index]).toString();
                        antecedentsAtts[ruleNumber].add(currentItem.substring(1, currentItem.indexOf("=")-1));
                        antecedentsVals[ruleNumber].add(currentItem.substring(currentItem.indexOf("=")+2, currentItem.length()-1));
                    }
                }
                //Output consequent
                if(linkRuleNode.next!=null){
                    short[] itemSet = linkRuleNode.consequent;
                    for (int index=0;index<itemSet.length;index++){
                        consequents[ruleNumber] = CBAFuzzy.AssocRuleMining.itemsTranslatorList.get(itemSet[index]).toString();
                    }
                }
                if(linkRuleNode.next!=null)
                    confidences[ruleNumber] = linkRuleNode.confidenceForRule;

                //Increment parameters
                ruleNumber++;
                linkRuleNode = linkRuleNode.next;
            }

            execUpdateRules(antecedentsAtts, antecedentsVals, consequents, confidences);
	}

	public static void execUpdateRules(LinkedList antecedentsAtts[], LinkedList antecedentsVals[], String consequents[], double confidences[]){
            String query;
            ResultSet rst;
            try {
                String insertStatement,insert1="", insert2="";
			
                Connection conexion=null;
                conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
                Statement instruccion = conexion.createStatement();

		for(int i=0; i<antecedentsAtts.length; i++){
                    query = "SELECT idregla FROM reglas ORDER BY idregla DESC limit 1";
                    rst = instruccion.executeQuery(query);
                    int id=0;
                    while(rst.next())
                        id=rst.getInt("idregla");
                    id++;

                    insert1="";
                    insert2="";
                    for(int j=0; j<antecedentsAtts[i].size();j++){
                        insert1 = insert1+""+antecedentsAtts[i].get(j).toString()+",";
                        insert2 = insert2+"'"+antecedentsVals[i].get(j).toString()+"',";
                    }
                    insert1 = insert1+"class,";
                    insert1 = insert1+"confianza)";
                    insert2 = insert2+"'"+consequents[i]+"', "+confidences[i]+")";
                    insertStatement = "INSERT INTO reglas (idregla,"+insert1+" VALUES ("+id+","+insert2;
                    System.out.println("Sentencias SQL: "+insertStatement);
                    instruccion.executeUpdate(insertStatement);
                }

        	conexion.close();
 		instruccion.close();
		} catch(SQLException e) {e.printStackTrace();
   	    }
	}
	
	public static void updateClusters(String[][] clusters, LinkedList attributes){
            String delete, insertStatement,insert1="", insert2="";
		
		try {
			Connection conexion=null;
                        conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
                        Statement instruccion = conexion.createStatement();
			
			delete = "DELETE FROM grupos";
                        instruccion.executeUpdate(delete);
        
	        for(int i=0; i<clusters.length; i++){
                    insert1="";
                    insert2="";
	        	for(int j=0; j<clusters[0].length; j++){
	        		String attName = attributes.get(j).toString();
	        		insert1 = insert1+attName+",";
	        		insert2 = insert2+"'"+clusters[i][j]+"',";
	        	}
	        	insert1 = insert1.substring(0, insert1.length()-1);
	        	insert1 = insert1+")";
	        	insert2 = insert2.substring(0, insert2.length()-1);
	        	insert2 = insert2+")";
	        	insertStatement = "INSERT INTO grupos (idgrupo,"+insert1+" VALUES ("+i+","+insert2;
                        instruccion.executeUpdate(insertStatement);
	        }
        	conexion.close();
 		instruccion.close();
	        
	    } catch(SQLException e) { e.printStackTrace();}
	}
	
	public static void updateListOfFreqItems(String clusteredData[][], LinkedList users, LinkedList series){
		String deleteStatement, insertStatement, updateStatement;
		int numRowsAccesses;
		try {
			Connection conexion=null;
                        conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
                        Statement instruccion = conexion.createStatement();

			deleteStatement = "DELETE FROM gruposeries";
                        instruccion.executeUpdate(deleteStatement);
	        
                        
	        numRowsAccesses = users.size();
	        for(int i=0; i<numRowsAccesses; i++){
	        	String clusterStr = clusteredData[i][clusteredData[0].length-1];
	        	int cluster_id = Integer.parseInt(clusterStr.substring(1, clusterStr.length()));
	        	if(isAlreadyThere(cluster_id, series.get(i).toString())){
	        		updateStatement = "UPDATE gruposeries SET contador=contador+1 WHERE idgrupo='"+cluster_id+"' AND idserie='"+series.get(i).toString()+"'";
		        	instruccion.executeUpdate(updateStatement);
	        	} else {
	        		insertStatement = "INSERT INTO gruposeries (idgrupo, idserie, contador) VALUES ('"+cluster_id+"','"+series.get(i).toString()+"',1)";
		        	instruccion.executeUpdate(insertStatement);
	        	}
	        }

        	conexion.close();
 		instruccion.close();

	    } catch(SQLException e) {e.printStackTrace();}
		
	}

	public static boolean isAlreadyThere(int cluster_id, String serie_id){
		String query;
		ResultSet rst;
		boolean output=false;

		try {
			Connection conexion=null;
                        conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","diego");
                        Statement instruccion = conexion.createStatement();
			
			query = "SELECT * FROM gruposeries WHERE idgrupo='"+cluster_id+"' AND idserie='"+serie_id+"'";
			rst = instruccion.executeQuery(query);
			if(rst.first())
				output = true;
			else
				output = false;
			
		    conexion.close();
                    instruccion.close();
                    rst.close();
                    return output;
        } catch(SQLException e) { 
   		    e.printStackTrace();
   		    return false;}
	}
}