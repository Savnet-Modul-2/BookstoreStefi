package com.example.SpringBookstore.dto.validation.information;

import jakarta.validation.GroupSequence;

@GroupSequence(value = {BasicInformation.class, AdvancedInformation.class})
public interface ValidationOrder {
}
