package com.objetdirect.gwt.umldrawer.client.beans;

import java.io.Serializable;
import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * 他の学生の保存履歴情報を保持するためのBeanクラス。
 * GWTのRPCで使用するため、IsSerializableとSerializableを実装します。
 */
public class SaveEventInfo implements IsSerializable, Serializable {
    private static final long serialVersionUID = 1L;
    private int editEventId;
    private String studentId;
    private Date saveDate;

    /**
     * GWTのRPCで必須のデフォルトコンストラクタ
     */
    public SaveEventInfo() {
    }

    public SaveEventInfo(int editEventId, String studentId, Date saveDate) {
        this.editEventId = editEventId;
        this.studentId = studentId;
        this.saveDate = saveDate;
    }

    public int getEditEventId() {
        return editEventId;
    }

    public String getStudentId() {
        return studentId;
    }

    public Date getSaveDate() {
        return saveDate;
    }
}

