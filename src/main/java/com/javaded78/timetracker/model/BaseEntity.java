package com.javaded78.timetracker.model;

import java.io.Serializable;

public interface BaseEntity <E extends Serializable> {

    E getId();
}
