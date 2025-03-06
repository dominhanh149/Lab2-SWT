package model;

import java.text.DecimalFormat;

public class Attendent {
    private int id;
    private WorkAssignment work;
    private int actualQuantity;
    private float alpha;
    private String note;

    // Định dạng số thập phân cho 2 chữ số
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public WorkAssignment getWork() {
        return work;
    }

    public void setWork(WorkAssignment work) {
        this.work = work;
    }

    public int getActualQuantity() {
        return actualQuantity;
    }

    public void setActualQuantity(int actualQuantity) {
        this.actualQuantity = actualQuantity;
    }

    public float getAlpha() {
        // Định dạng và trả về giá trị với 2 chữ số thập phân
        return Float.parseFloat(decimalFormat.format(alpha));
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
