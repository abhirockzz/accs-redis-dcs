package com.oracle.cloud.acc.redis;

import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Todo {
    private String name;
    private String detail;

    public Todo() {
    }
    
    

    public Todo(String name, String detail) {
        this.name = name;
        this.detail = detail;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Todo other = (Todo) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.detail, other.detail)) {
            return false;
        }
        return true;
    }
    
    
    
}
