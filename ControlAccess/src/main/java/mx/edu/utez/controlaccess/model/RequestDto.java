package mx.edu.utez.controlaccess.model;

public class RequestDto {
    private String data;

    public RequestDto() {
    }

public RequestDto(String data) {
        this.data = data;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
}
