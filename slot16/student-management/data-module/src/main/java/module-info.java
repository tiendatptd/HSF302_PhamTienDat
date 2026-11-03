module data.module {
    // Khai báo các thư viện mà data-module sử dụng
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires static lombok;

    // Cho phép các module khác (như ui-module) sử dụng các class trong 2 package này
    exports com.hsf302.entity;
    exports com.hsf302.repository;

    // Cho phép Hibernate truy cập vào Entity thông qua Reflection để map dữ liệu
    opens com.hsf302.entity to org.hibernate.orm.core;
}