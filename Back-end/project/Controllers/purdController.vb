Imports System.Net
Imports System.Web.Http
Imports Dapper

Namespace Controllers


    Public Class purdStatusController
        Inherits ApiController

        ' POST: api/purdStatus
        Public Function PostValue(<FromBody()> ByVal value As purdStatus)
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            Dim ipd As String = value.ipd
            Dim istatus As String = value.istatus
            Dim dchgdate As String = Format(Now, "yyyy-MM-dd HH:mm:ss")

            s = "select ipd from purd_data where ipd = @ipd;"
            Dim rs As List(Of purd_data) = conn.oConn.Query(Of purd_data)(s, New With {.ipd = ipd})

            If rs.Count < 1 Then
                If rs.FindAll(Function(x) x.ipd = ipd).Count < 1 Then
                    feedback.rid = -1
                    feedback.message = "不存在"
                    Return feedback
                End If
            End If

            Dim purdStatuses As New List(Of purdStatus)
            Dim purdStatus As New purdStatus
            purdStatus.ipd = ipd
            purdStatus.istatus = istatus
            purdStatuses.Add(purdStatus)
            s = String.Format("update `project`.`purd_data` set istatus = @istatus, dchgdate = '{0}' where ipd = @ipd;", dchgdate)
            conn.oConn.Execute(s, purdStatuses)

            feedback.rid = 1
            feedback.message = "更新成功"
            Return feedback

        End Function
    End Class

    Public Class purdDataController
        Inherits ApiController

        ' POST: api/purdData
        Public Function PostValue(<FromBody()> ByVal value As purd_data)
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            Dim ivender As String = value.ivender
            Dim nname As String = value.nname
            Dim qprice As Double = value.qprice
            Dim qquantity As Integer = value.qquantity
            Dim itype As String = value.itype
            Dim dindate As String = value.dindate
            Dim dlinedate As String = value.dlinedate
            Dim dfinalprice As Double = value.dfinalprice
            Dim dchgdate = Format(Now, "yyyy-MM-dd HH:mm:ss")

            Dim purd_datas As New List(Of purd_data)
            Dim purd_data As New purd_data
            purd_data.ivender = ivender
            purd_data.nname = nname
            purd_data.qprice = qprice
            purd_data.qquantity = qquantity
            purd_data.itype = itype
            purd_data.dindate = dindate
            purd_data.dlinedate = dlinedate
            purd_data.dfinalprice = dfinalprice
            purd_datas.Add(purd_data)
            s = String.Format("INSERT INTO `project`.`purd_data` (`ivender`, `nname`, `qprice`, `qquantity`, `itype`, `dindate`, `dlinedate`, `dfinalprice`, `dchgdate`, `istatus`) VALUES (@ivender, @nname, @qprice, @qquantity, @itype, @dindate, @dlinedate, @dfinalprice, '{0}', 'F');", dchgdate)
            conn.oConn.Execute(s, purd_datas)

            feedback.rid = 1
            feedback.message = "新增成功"
            Return feedback
        End Function

        ' PUT: api/purdData/5
        Public Function PutValue(ByVal ipd As String, <FromBody()> ByVal value As purd_data)

            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            Dim u_ivender As String = value.ivender
            Dim u_nname As String = value.nname
            Dim u_qprice As Double = value.qprice
            Dim u_qquantity As Integer = value.qquantity
            Dim u_itype As String = value.itype
            Dim u_dindate As String = value.dindate
            Dim u_dlinedate As String = value.dlinedate
            Dim u_dfinalprice As Double = value.dfinalprice
            Dim u_dchgdate = Format(Now, "yyyy-MM-dd HH:mm:ss")

            s = "select ipd from purd_data where ipd = @ipd;"
            Dim rs As List(Of purd_data) = conn.oConn.Query(Of purd_data)(s, New With {.ipd = ipd})

            If rs.Count < 1 Then
                If rs.FindAll(Function(x) x.ipd = ipd).Count < 1 Then
                    feedback.rid = -1
                    feedback.message = "不存在"
                    Return feedback
                End If
            End If

            Dim purd_datas As New List(Of purd_data)
            Dim purd_data As New purd_data
            purd_data.ivender = u_ivender
            purd_data.nname = u_nname
            purd_data.qprice = u_qprice
            purd_data.qquantity = u_qquantity
            purd_data.itype = u_itype
            purd_data.dindate = u_dindate
            purd_data.dlinedate = u_dlinedate
            purd_data.dfinalprice = u_dfinalprice
            purd_datas.Add(purd_data)
            s = String.Format("UPDATE `project`.`purd_data` set `ivender` = @ivender, `nname` = @nname, `qprice` = @qprice, `qquantity` = @qquantity, `itype` = @itype, `dindate` = @dindate, `dlinedate` = @dlinedate, `dfinalprice` = @dfinalprice, `dchgdate` = '{0}' WHERE ipd = '{1}';", u_dchgdate, ipd)
            conn.oConn.Execute(s, purd_datas)

            feedback.rid = 1
            feedback.message = "更新成功"
            Return feedback
        End Function

        ' DELETE: api/purdData/5
        Public Function DeleteValue(ByVal ipd As String)
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            Dim dchgdate As String = Format(Now, "yyyy-MM-dd HH:mm:ss")

            s = "select ipd from purd_data where ipd = @ipd and istatus <> 'D';"
            Dim rs As List(Of purd_data) = conn.oConn.Query(Of purd_data)(s, New With {.ipd = ipd})

            If rs.Count < 1 Then
                If rs.FindAll(Function(x) x.ipd = ipd).Count < 1 Then
                    feedback.rid = -1
                    feedback.message = "不存在"
                    Return feedback
                End If
            End If

            Dim purd_datas As New List(Of purd_data)
            Dim purd_data As New purd_data
            purd_data.ipd = ipd
            purd_datas.Add(purd_data)
            s = String.Format("update `project`.`purd_data` set istatus = 'D', dchgdate = '{0}' where ipd = @ipd;", dchgdate)
            conn.oConn.Execute(s, purd_datas)

            feedback.rid = 1
            feedback.message = "成功刪除"
            Return feedback
        End Function
    End Class
End Namespace

Public Class purdStatus
    Public Property ipd As Integer
    Public Property istatus As String
End Class