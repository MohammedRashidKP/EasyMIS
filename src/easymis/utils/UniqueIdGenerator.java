/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easymis.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 *
 * @author RashidKP
 */
public class UniqueIdGenerator {
    public static String getUniqueId(){
        LocalDateTime localDate = LocalDateTime.now();
        return String.valueOf(localDate.toEpochSecond(ZoneOffset.UTC));
    }
}
