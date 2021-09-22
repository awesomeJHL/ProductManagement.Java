package com.fourtitude.asia.product.utils;

import java.io.*;
import java.util.*;
import org.slf4j.*;
import org.springframework.stereotype.Component;
import com.fourtitude.asia.product.model.AddProductParam;

public class ProductStorage {

    private static final Logger LOG = LoggerFactory.getLogger(ProductStorage.class);

    private static final Map<String, Integer> T_PRODUCT_FIELD_LENGTH = new HashMap<>();

    static {
        T_PRODUCT_FIELD_LENGTH.put("code", 9);
        T_PRODUCT_FIELD_LENGTH.put("name", 90);
        T_PRODUCT_FIELD_LENGTH.put("category", 28);
        T_PRODUCT_FIELD_LENGTH.put("brand", 28);
        T_PRODUCT_FIELD_LENGTH.put("type", 21);
        T_PRODUCT_FIELD_LENGTH.put("description", 180);
    }

    public static final int MAX_ITEMS = 1000;

    public static final String NULL_VALUE = "*NULL*";
    public static final String LOAD_FILE_NAME = "products.data.txt";
    
    public ProductStorage() {
    }

    private void checkLength(/*@NotNull*/ String fieldName, /*@NotNull*/ String value) {
        Integer length = T_PRODUCT_FIELD_LENGTH.get(fieldName);
        if (length == null) {
            throw new RuntimeException("field=[" + fieldName +
                    "], max length is not defined");
        }
        if (value.length() > length) {
            throw new RuntimeException("field=[" + fieldName +
                    "], has length=[" + value.length() +
                    "] over max length=[" + length +
                    "]");
        }
    }

    public ArrayList<AddProductParam> loadDataset() {
    	
    	System.out.println("=========ArrayList===========");
    	
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                ProductStorage.class.getResourceAsStream(LOAD_FILE_NAME)
        ))) {
            LOG.debug("start loading dataset");

            boolean started = false;
            
            ArrayList<AddProductParam> products = new ArrayList<AddProductParam>();
            AddProductParam product = new AddProductParam();
            
            String line = null;
            int lineNum = 0;
            
            while ((line = br.readLine()) != null) {
                lineNum += 1;
                if (line.isEmpty()) {
                    continue;
                } else if (line.startsWith("#")) {
                    continue;
                }
                // TODO: should have better validation
                String[] flds = line.split("=", 2);
                if (flds.length == 2) {
                	
                    String key = flds[0].trim();
                    String val = flds[1].trim();
                    switch (key) {
                    case "code":
                        checkLength("code", val);
                        if (started) {
                        	products.add(product);
                        }
                        //int productId = seqProduct.incrementAndGet();
                        product = new AddProductParam();
                        //product.setId(productId);
                        product.setCode(val);
                        started = true;
                        break;
                    case "name":
                        checkLength("name", val);
                        if (started) {
                            product.setName(val);
                        }
                        break;
                    case "category":
                        checkLength("category", val);
                        if (started) {
                            product.setCategory(val);
                        }
                        break;
                    case "brand":
                        checkLength("brand", val);
                        if (started) {
                            product.setBrand(val);
                        }
                        break;
                    case "type":
                        checkLength("type", val);
                        if (started) {
                            product.setType(val);
                        }
                        break;
                    case "description":
                        checkLength("description", val);
                        if (started) {
                            product.setDescription(val);
                        }
                        break;
                    default:
                        throw new RuntimeException("not catered for field at lineNum=[" + lineNum + "]");
                    }
                }
            }
            return products;
        } catch (Exception ex) {
            throw new RuntimeException("fail loading dataset", ex);
        }
    }
}