package Server;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Register {
	
	public static void main(String args[]) {
		
		Register r = new Register();
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Nome? ");
		String nome = sc.nextLine();
		
		int dia;
		String mes;
		int ano;
		
		do {
			System.out.println("Data Nascimento - Formato dd/mm/aaaa");
			System.out.print("Dia? ");
			dia = sc.nextInt();
			
			sc.nextLine();
			
			System.out.print("Mes? ");
			mes = sc.nextLine();
				
			System.out.print("Ano? ");
			ano = sc.nextInt();
			//String data = dia+"/"+mes+"/"+ano;
		} while (!r.diaMesValido(dia, mes, ano));
		
		System.out.println("N�mero? ");
		int numero = sc.nextInt();	
	
		if (mes.length() == 1) {
			mes = "0"+Integer.parseInt(mes);
		}
		
		String data = dia+"/"+mes+"/"+ano;
		
		r.registarAluno(nome, data, numero);
	}
	
	
	
	public boolean diaMesValido(int dia, String mes, int ano) {
		//dias entre 1 e 31
		if (dia < 1 && dia > 31) {
			System.out.println("dia inv�lido");
			return false;
		}
		
		//tamanho mes 1 ou 2
		if (mes.length() < 1 || mes.length() > 2) {
			System.out.println("mes invalido");
			return false;
		}
		
		//s� pode ter numeros
		for (int i = 0; i < mes.length(); i++) {
			char a = mes.charAt(i);
			if (!Character.isDigit(a)) {
				System.out.println("mes invalido");
				return false;
			}
		}
			
		//dias 30
		if (mes.equals("04") || mes.equals("06") || mes.equals("09") || mes.equals("11")) {
			if (dia == 31) {
				System.out.println("dia ou mes incorrectos");
				return false;
			}
		}
		//fevereiro
		else if (mes.equals("02")) {
			if (ano%4 == 0) { //ano bisexto
				if (dia > 29) {
					System.out.println("dia, mes ou ano incorrecto");
					return false;
				}
			}
			else {
				if (dia > 28) {
					System.out.println("dia, mes ou ano incorrecto");
					return false;
				}
			}					
		}
		
		return true;
	}
	
	
	
	public void registarAluno(String nome, String data, int numero) {
		
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse("alunos.xml");
			DOMSource source = new DOMSource(document);
			
			Element root = document.getDocumentElement();
		
			Element novoAluno = document.createElement("aluno");
			((Element) novoAluno).setAttribute("numero", Integer.toString(numero));
			((Element) novoAluno).setAttribute("data_nascimento", data);
			
			Element nomeAluno = document.createElement("nome");
			nomeAluno.appendChild(document.createTextNode(nome));
			novoAluno.appendChild(nomeAluno);
			
			root.appendChild(novoAluno);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			StreamResult result = new StreamResult("alunos.xml");
			transformer.transform(source, result);
			
			File f = new File("alunos.xml");
			PrintWriter write = new PrintWriter("correctAlunos.xml");
			removeEmptyLines(f, write);
		} 
		catch (Exception e) {
			
		}
	}
	
	public void removeEmptyLines(File f, PrintWriter write) throws FileNotFoundException {
		Scanner file = new Scanner(f);
		
		boolean copia = false;
		
		while(file.hasNextLine()) {
			String linha = file.nextLine();
			for (int i = 0; i < linha.length(); i++) {
				char a = linha.charAt(i);
				if (Character.isLetterOrDigit(a)) {
					copia = true;
				}
			}
			if (copia) {
				write.write(linha+"\n");
				copia = false;
			}

        }
        write.close();
        file.close();
		
	}

}
