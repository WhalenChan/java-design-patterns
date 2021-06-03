package com.iluwatar.abstractdocument.domain;

import com.iluwatar.abstractdocument.Document;
import com.iluwatar.abstractdocument.domain.enums.Property;

import java.util.Optional;

/**
 * HasWeight trait for static access to 'weight' property.
 * 包含重量特征。
 *
 * @author chenrunhui
 * @date 2021/6/2 11:33
 */
public interface HasWeight extends Document {

    default Optional<String> getWeight() {
        return Optional.ofNullable((String) get(Property.WEIGHT.toString()));
    }

}
