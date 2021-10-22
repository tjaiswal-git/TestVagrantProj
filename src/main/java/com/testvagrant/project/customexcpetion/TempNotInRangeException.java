package com.testvagrant.project.customexcpetion;

public class TempNotInRangeException extends Exception{

        public TempNotInRangeException(String errorMessage)
        {
            super(errorMessage);
        }
    }

