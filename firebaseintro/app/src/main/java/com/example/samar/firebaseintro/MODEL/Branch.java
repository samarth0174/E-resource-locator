package com.example.samar.firebaseintro.MODEL;

public class Branch {

    private  String BranchCode;
    private  String BranchName;

    public Branch() {
    }

    public Branch(String branchCode, String branchName) {
        BranchCode = branchCode;
        BranchName = branchName;
    }

    public String getBranchCode() {
        return BranchCode;
    }

    public void setBranchCode(String branchCode) {
        BranchCode = branchCode;
    }

    public String getBranchName() {
        return BranchName;
    }

    public void setBranchName(String branchName) {
        BranchName = branchName;
    }
}
