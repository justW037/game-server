package com.example.demo.common.constants;

public interface Message {
	String OK = "Thành công";
	String ERROR = "Có lỗi xảy ra";
	String ERROR_400 = "Yêu cầu không hợp lệ.";
	String ERROR_401 = "Truy cập trái phép.";
	String ERROR_403 = "Truy cập bị cấm.";
	String ERROR_404 = "Không tìm thấy dữ liệu yêu cầu";
	String ERROR_500 = "Hệ thống đang bận. Vui lòng thử lại sau";

	String MSG_CREATE_SUCCESS = "Thêm mới dữ liệu thành công";
	String MSG_CREATE_FAILURE = "Thêm mới dữ liệu không thành công";
	String MSG_UPDATE_SUCCESS = "Cập nhật dữ liệu thành công";
	String MSG_UPDATE_FAILURE = "Cập nhật dữ liệu không thành công";
	String MSG_DESTROY_SUCCESS = "Xóa dữ liệu thành công";
	String MSG_DESTROY_FAILURE = "Xóa dữ liệu không thành công";
	String MSG_NO_DATA = "Không có dữ liệu";
	String MSG_ACCESS_DENIED = "Truy cập bị từ chối.";

}

