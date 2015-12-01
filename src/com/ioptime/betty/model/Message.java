package com.ioptime.betty.model;

public class Message {
	int message_id;
	int message_to;

	public int getMessage_id() {
		return message_id;
	}

	public void setMessage_id(int message_id) {
		this.message_id = message_id;
	}

	public int getMessage_to() {
		return message_to;
	}

	public void setMessage_to(int message_to) {
		this.message_to = message_to;
	}

	public Message(int message_id, int message_to, String message_subject,
			String message_status, String group_message,
			String group_message_id, String message_created_date,
			String message_reference_id, String message_sub_reference_id,
			String message_customer_id, String message_user_id,
			String message_order_id, String message_last_update,
			String message_last_modified, int customer_read,
			int fcustomer_read, int support_read, int user_type,
			int admin_status, int vendor_status, int customer_status,
			int transfer_status, int transfer_message_id, String attachment,
			int content_id, String content, int is_user, int sender_id,
			int reply_status, int message_read_customer,
			int message_read_support, String created, String file) {
		super();
		this.message_id = message_id;
		this.message_to = message_to;
		this.message_subject = message_subject;
		this.message_status = message_status;
		this.group_message = group_message;
		this.group_message_id = group_message_id;
		try {
			// parsing date to date and time
			String dateTime[] = message_created_date.split(" ");
			this.message_created_date = dateTime[0];
			this.message_created_time = dateTime[1];
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.message_reference_id = message_reference_id;
		this.message_sub_reference_id = message_sub_reference_id;
		this.message_customer_id = message_customer_id;
		this.message_user_id = message_user_id;
		this.message_order_id = message_order_id;
		this.message_last_update = message_last_update;
		this.message_last_modified = message_last_modified;
		this.customer_read = customer_read;
		this.fcustomer_read = fcustomer_read;
		this.support_read = support_read;
		this.user_type = user_type;
		this.admin_status = admin_status;
		this.vendor_status = vendor_status;
		this.customer_status = customer_status;
		this.transfer_status = transfer_status;
		this.transfer_message_id = transfer_message_id;
		Attachment = attachment;
		this.content_id = content_id;
		this.content = content;
		this.is_user = is_user;
		this.sender_id = sender_id;
		this.reply_status = reply_status;
		this.message_read_customer = message_read_customer;
		this.message_read_support = message_read_support;
		this.created = created;
		this.file = file;
	}

	public String getMessage_subject() {
		return message_subject;
	}

	public void setMessage_subject(String message_subject) {
		this.message_subject = message_subject;
	}

	public String getMessage_status() {
		return message_status;
	}

	public void setMessage_status(String message_status) {
		this.message_status = message_status;
	}

	public String getGroup_message() {
		return group_message;
	}

	public void setGroup_message(String group_message) {
		this.group_message = group_message;
	}

	public String getGroup_message_id() {
		return group_message_id;
	}

	public void setGroup_message_id(String group_message_id) {
		this.group_message_id = group_message_id;
	}

	public String getMessage_created_date() {
		return message_created_date;
	}

	public void setMessage_created_date(String message_created_date) {
		this.message_created_date = message_created_date;
	}

	public String getMessage_created_time() {
		return message_created_time;
	}

	public void setMessage_created_time(String message_created_time) {
		this.message_created_time = message_created_time;
	}

	public String getMessage_reference_id() {
		return message_reference_id;
	}

	public void setMessage_reference_id(String message_reference_id) {
		this.message_reference_id = message_reference_id;
	}

	public String getMessage_sub_reference_id() {
		return message_sub_reference_id;
	}

	public void setMessage_sub_reference_id(String message_sub_reference_id) {
		this.message_sub_reference_id = message_sub_reference_id;
	}

	public String getMessage_customer_id() {
		return message_customer_id;
	}

	public void setMessage_customer_id(String message_customer_id) {
		this.message_customer_id = message_customer_id;
	}

	public String getMessage_user_id() {
		return message_user_id;
	}

	public void setMessage_user_id(String message_user_id) {
		this.message_user_id = message_user_id;
	}

	public String getMessage_order_id() {
		return message_order_id;
	}

	public void setMessage_order_id(String message_order_id) {
		this.message_order_id = message_order_id;
	}

	public String getMessage_last_update() {
		return message_last_update;
	}

	public void setMessage_last_update(String message_last_update) {
		this.message_last_update = message_last_update;
	}

	public String getMessage_last_modified() {
		return message_last_modified;
	}

	public void setMessage_last_modified(String message_last_modified) {
		this.message_last_modified = message_last_modified;
	}

	public int getCustomer_read() {
		return customer_read;
	}

	public void setCustomer_read(int customer_read) {
		this.customer_read = customer_read;
	}

	public int getFcustomer_read() {
		return fcustomer_read;
	}

	public void setFcustomer_read(int fcustomer_read) {
		this.fcustomer_read = fcustomer_read;
	}

	public int getSupport_read() {
		return support_read;
	}

	public void setSupport_read(int support_read) {
		this.support_read = support_read;
	}

	public int getUser_type() {
		return user_type;
	}

	public void setUser_type(int user_type) {
		this.user_type = user_type;
	}

	public int getAdmin_status() {
		return admin_status;
	}

	public void setAdmin_status(int admin_status) {
		this.admin_status = admin_status;
	}

	public int getVendor_status() {
		return vendor_status;
	}

	public void setVendor_status(int vendor_status) {
		this.vendor_status = vendor_status;
	}

	public int getCustomer_status() {
		return customer_status;
	}

	public void setCustomer_status(int customer_status) {
		this.customer_status = customer_status;
	}

	public int getTransfer_status() {
		return transfer_status;
	}

	public void setTransfer_status(int transfer_status) {
		this.transfer_status = transfer_status;
	}

	public int getTransfer_message_id() {
		return transfer_message_id;
	}

	public void setTransfer_message_id(int transfer_message_id) {
		this.transfer_message_id = transfer_message_id;
	}

	public String getAttachment() {
		return Attachment;
	}

	public void setAttachment(String attachment) {
		Attachment = attachment;
	}

	public int getContent_id() {
		return content_id;
	}

	public void setContent_id(int content_id) {
		this.content_id = content_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getIs_user() {
		return is_user;
	}

	public void setIs_user(int is_user) {
		this.is_user = is_user;
	}

	public int getSender_id() {
		return sender_id;
	}

	public void setSender_id(int sender_id) {
		this.sender_id = sender_id;
	}

	public int getReply_status() {
		return reply_status;
	}

	public void setReply_status(int reply_status) {
		this.reply_status = reply_status;
	}

	public int getMessage_read_customer() {
		return message_read_customer;
	}

	public void setMessage_read_customer(int message_read_customer) {
		this.message_read_customer = message_read_customer;
	}

	public int getMessage_read_support() {
		return message_read_support;
	}

	public void setMessage_read_support(int message_read_support) {
		this.message_read_support = message_read_support;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	String message_subject;
	String message_status;
	String group_message;
	String group_message_id;
	String message_created_date;
	String message_created_time;
	String message_reference_id;
	String message_sub_reference_id;
	String message_customer_id;
	String message_user_id;
	String message_order_id;
	String message_last_update;
	String message_last_modified;
	int customer_read;
	int fcustomer_read;
	int support_read;
	/////user type is reciever
	int user_type;
	int admin_status;
	int vendor_status;
	int customer_status;
	int transfer_status;
	int transfer_message_id;
	String Attachment;
	int content_id;
	String content;
	int is_user;
	////reciever id
	int sender_id;
	int reply_status;
	int message_read_customer;
	int message_read_support;
	String created;
	String file;
}
