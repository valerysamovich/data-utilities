package com.samovich.converters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * Converts the .xls file to .csv
 */
public class XlsToCsv {

    private static List<List<HSSFCell>> cellGrid;

    public static void main(String[] args) {
        try {
            convertFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void convertFile() throws IOException {

        try {
            cellGrid = new ArrayList<List<HSSFCell>>();
            // Location of the input .xls file
            FileInputStream myInput = new FileInputStream("src/main/resources/input.xls");
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);
            Iterator<?> rowIter = mySheet.rowIterator();

            while (rowIter.hasNext()) {
                HSSFRow myRow = (HSSFRow) rowIter.next();
                Iterator<?> cellIter = myRow.cellIterator();
                List<HSSFCell> cellRowList = new ArrayList<HSSFCell>();
                while (cellIter.hasNext()) {
                    HSSFCell myCell = (HSSFCell) cellIter.next();
                    cellRowList.add(myCell);
                }
                cellGrid.add(cellRowList);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Output .csv file
        File file = new File("src/main/resources/output.csv");
        PrintStream stream = new PrintStream(file);
        for (int i = 0; i < cellGrid.size(); i++) {
            List<HSSFCell> cellRowList = cellGrid.get(i);
            for (int j = 0; j < cellRowList.size(); j++) {
                HSSFCell myCell = (HSSFCell) cellRowList.get(j);
                String stringCellValue = myCell.toString();
                stream.print(stringCellValue + ",");
            }
            stream.println("");
        }
    }
}