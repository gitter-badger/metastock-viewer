package org.jsoftware.mstock.tests;

import java.util.ArrayList;
import java.util.Collection;

public class TestResult implements Comparable<TestResult> {
    private final Collection<String> errorMessages = new ArrayList<String>();
    private final Collection<String> infoMessages = new ArrayList<String>();
    private final AbstractMstockTest test;

    public TestResult(AbstractMstockTest test) {
        this.test = test;
    }

    public boolean isOk() {
        return errorMessages.isEmpty();
    }

    public Collection<String> getErrorMessages() {
        return errorMessages;
    }

    public boolean isImportant() {
        return test.isImportant();
    }

    @Override
    public String toString() {
        return isOk() ? "OK" : toString(errorMessages);
    }

    public void addMessage(String msg) {
        errorMessages.add(msg);
    }

    public void addInfo(String msg) {
        infoMessages.add(msg);
    }

    public Collection<String> getInfoMessages() {
        return infoMessages;
    }


    public int compareTo(TestResult o) {
        return this.errorMessages.size() - o.errorMessages.size();
    }

    public static String toString(Collection<String> messages) {
        StringBuilder b = new StringBuilder();
        for (String s : messages) {
            b.append(s).append("; ");
        }
        return b.toString().trim();
    }
}
