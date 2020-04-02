package com.example.elasticsearch.marshal;

public class _shards
{
    private int total;

    private int successful;

    private int skipped;

    private int failed;

    public void setTotal(int total){
        this.total = total;
    }
    public int getTotal(){
        return this.total;
    }
    public void setSuccessful(int successful){
        this.successful = successful;
    }
    public int getSuccessful(){
        return this.successful;
    }
    public void setSkipped(int skipped){
        this.skipped = skipped;
    }
    public int getSkipped(){
        return this.skipped;
    }
    public void setFailed(int failed){
        this.failed = failed;
    }
    public int getFailed(){
        return this.failed;
    }
}