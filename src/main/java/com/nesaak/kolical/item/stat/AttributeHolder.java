package com.nesaak.kolical.item.stat;

import java.util.List;
import java.util.Map;

public interface AttributeHolder {

    Map<ItemStat, Double> getStats();

    List<ItemModifier> getModifiers();

}
