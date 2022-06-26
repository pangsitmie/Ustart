Imports System.Net
Imports System.Web.Http
Imports Dapper

Namespace Controllers
    Public Class venderController
        Inherits ApiController

        ' GET: api/vender
        Public Function GetValues()
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            s = "select ivender,nname,nman,eemail,ephone,eaddress from vender where istatus = 'T';"
            Dim rs As List(Of vender) = conn.oConn.Query(Of vender)(s)
            If rs.Count > 0 Then
                Return rs
            Else
                feedback.rid = -1
                feedback.message = "查無資料"
                Return feedback
            End If
        End Function

        ' GET: api/vender/5
        Public Function GetValue(ByVal ivender As String)
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            s = "select ivender,nname,nman,eemail,ephone,eaddress from vender where ivender = @ivender and istatus = 'T';"
            Dim rs As List(Of vender) = conn.oConn.Query(Of vender)(s, New With {.ivender = ivender})

            If rs.Count > 0 Then
                Return rs(0)
            Else
                feedback.rid = -1
                feedback.message = "查無資料"
                Return feedback
            End If
        End Function

        ' POST: api/vender
        Public Function PostValue(<FromBody()> ByVal value As vender)
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            Dim ivender As String = value.ivender
            Dim nname As String = value.nname
            Dim nman As String = value.nman
            Dim eemail As String = value.eemail
            Dim ephone As String = value.ephone
            Dim eaddress As String = value.eaddress
            Dim dchgdate As String = Format(Now, "yyyy-MM-dd HH:mm:ss")

            s = "select ivender from vender where ivender = @ivender and istatus = 'T';"
            Dim rs As List(Of vender) = conn.oConn.Query(Of vender)(s, New With {.ivender = ivender})

            If rs.Count > 0 Then
                If rs.FindAll(Function(x) x.ivender = ivender).Count > 0 Then
                    feedback.rid = -1
                    feedback.message = "該代號已經被註冊"
                    Return feedback
                End If
            End If

            Dim venders As New List(Of vender)
            Dim vender As New vender
            vender.ivender = ivender
            vender.nname = nname
            vender.nman = nman
            vender.eemail = eemail
            vender.ephone = ephone
            vender.eaddress = eaddress
            venders.Add(vender)
            s = String.Format("INSERT INTO `project`.`vender` (`ivender`, `nname`, `nman`, `eemail`, `ephone`, `eaddress`, `dchgdate`) VALUES (@ivender, @nname, @nman, @eemail, @ephone, @eaddress, '{0}');", dchgdate)
            conn.oConn.Execute(s, venders)

            feedback.rid = 1
            feedback.message = "註冊成功"
            Return feedback
        End Function

        ' PUT: api/vender/5
        Public Function PutValue(ByVal ivender As String, <FromBody()> ByVal value As vender)

            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            Dim u_ivender As String = value.ivender
            Dim u_nname As String = value.nname
            Dim u_nman As String = value.nman
            Dim u_eemail As String = value.eemail
            Dim u_ephone As String = value.ephone
            Dim u_eaddress As String = value.eaddress
            Dim u_dchgdate As String = Format(Now, "yyyy-MM-dd HH:mm:ss")

            s = "select ivender from vender where ivender = @ivender and istatus = 'T';"
            Dim rs As List(Of vender) = conn.oConn.Query(Of vender)(s, New With {.ivender = ivender})

            If rs.Count < 1 Then
                If rs.FindAll(Function(x) x.ivender = ivender).Count < 1 Then
                    feedback.rid = -1
                    feedback.message = "不存在"
                    Return feedback
                End If
            End If

            If ivender <> u_ivender Then
                s = "select ivender from vender where ivender = @ivender and istatus = 'T';"
                rs.Clear()
                rs = conn.oConn.Query(Of vender)(s, New With {.ivender = u_ivender})

                If rs.Count > 0 Then
                    If rs.FindAll(Function(x) x.ivender = u_ivender).Count > 0 Then
                        feedback.rid = -1
                        feedback.message = "該代號已經被註冊"
                        Return feedback
                    End If
                End If
            End If

            Dim venders As New List(Of vender)
            Dim vender As New vender
            vender.ivender = u_ivender
            vender.nname = u_nname
            vender.nman = u_nman
            vender.eemail = u_eemail
            vender.ephone = u_ephone
            vender.eaddress = u_eaddress
            venders.Add(vender)
            s = String.Format("UPDATE `project`.`vender` set `ivender` = @ivender, `nname` = @nname, `nman` = @nman, `eemail` = @eemail, `ephone` = @ephone, `eaddress` = @eaddress, `dchgdate` = '{0}' WHERE ivender = '{1}' AND istatus = 'T';", u_dchgdate, ivender)
            conn.oConn.Execute(s, venders)

            feedback.rid = 1
            feedback.message = "更新成功"
            Return feedback
        End Function

        ' DELETE: api/vender/5
        Public Function DeleteValue(ByVal ivender As String)
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            Dim dchgdate As String = Format(Now, "yyyy-MM-dd HH:mm:ss")

            s = "select ivender from vender where ivender = @ivender and istatus <> 'D';"
            Dim rs As List(Of vender) = conn.oConn.Query(Of vender)(s, New With {.ivender = ivender})

            If rs.Count < 1 Then
                If rs.FindAll(Function(x) x.ivender = ivender).Count < 1 Then
                    feedback.rid = -1
                    feedback.message = "不存在"
                    Return feedback
                End If
            End If

            Dim venders As New List(Of vender)
            Dim vender As New vender
            vender.ivender = ivender
            venders.Add(vender)
            s = String.Format("update `project`.`vender` set istatus = 'D', dchgdate = '{0}' where ivender = @ivender;", dchgdate)
            conn.oConn.Execute(s, venders)

            feedback.rid = 1
            feedback.message = "成功刪除"
            Return feedback
        End Function
    End Class
End Namespace