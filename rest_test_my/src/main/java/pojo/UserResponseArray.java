package pojo;

import java.util.List;

public class UserResponseArray {
  private String meta;
  private List<DataUser> data;

  public String getMeta() {
    return meta;
  }

  public void setMeta(String meta) {
    this.meta = meta;
  }

  public List<DataUser> getData() {
    return data;
  }

  public void setData(List<DataUser> data) {
    this.data = data;
  }
}
