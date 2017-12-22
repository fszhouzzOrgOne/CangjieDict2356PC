package cjdict2356pc.dto.compare;

import java.util.Comparator;

import cjdict2356pc.dto.Item;

public class DefaultItemComparator implements Comparator<Item> {

    @Override
    public int compare(Item one, Item two) {
        String encode1 = one.getEncode();
        String encode2 = two.getEncode();
        // 不是漢字
        if (null == encode1 || null == encode2) {
            if (null == encode1) {
                return 1; // 编码爲空，在最後
            } else {
                return -1;
            }
        } else if (encode1.length() > encode2.length()) {
            return 1;
        } else if (encode1.length() == encode2.length()) {
            if (one.getCharacter().length() > two.getCharacter().length()) {
                return 1;
            } else if (one.getCharacter().length() == two.getCharacter()
                    .length()) {
                int encodeCompared = encode1.compareTo(encode2);

                if (0 == encodeCompared) {
                    return one.getCharacter().compareTo(two.getCharacter());
                } else {
                    return encodeCompared;
                }
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

}
