<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<html>

<body>
<h2>Spring MVC - Uploading a file.. </h2>
    <spring:form method="POST" commandName="/fileUpload" enctype="multipart/form-data">

        Upload your file please:
        <input type="file" name="file" />
        <input type="submit" value="upload" />
        <label>
            <input type="checkbox" name="fullImport" value="true"><span>Full Import</span>
        </label>
        <spring:errors path="file" cssStyle="color: #ff0000;" />
    </spring:form>


</body>
</html>
