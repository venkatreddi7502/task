package CsvParser.java;

import com.example.task.entity.Details;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CsvParser {

    private static final DataFormatter dataFormatter = new DataFormatter();
    public static List<Details> parse(InputStream inputStream) throws IOException {
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet

        List<Details> entities = new ArrayList<>();

        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            // Skip header row if needed
            if (row.getRowNum() == 0) {
                continue;
            }

            Details entity = new Details();
            entity.setName(getStringCellValue(row.getCell(1)));
            entity.setBatch(getStringCellValue(row.getCell(2)));
            entity.setStock((int) getNumericCellValue(row.getCell(3)));
            entity.setSupplier(getStringCellValue(row.getCell(10)));
          entity.setExp(parseDate(getStringCellValue(row.getCell(8))));
//            entity.setExp(parseDate(String.valueOf(row.getCell(8).getDateCellValue())));
            System.out.println(row.getCell(4).getDateCellValue());
            entities.add(entity);
        }

        workbook.close();
        return entities;
    }

    private static String getStringCellValue(Cell cell) {
        return cell != null ? dataFormatter.formatCellValue(cell) : null;
    }


    private static double getNumericCellValue(Cell cell) {
        if (cell != null) {
            if (cell.getCellType() == CellType.NUMERIC) {
                return cell.getNumericCellValue();
            } else if (cell.getCellType() == CellType.STRING) {
                try {
                    return Double.parseDouble(cell.getStringCellValue());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    private static Date parseDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
