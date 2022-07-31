Imports System.Net
Imports System.Web.Http
Imports Dapper

Namespace Controllers
    Public Class loginController
        Inherits ApiController

        ' POST: api/login
        Public Function PostValue(<FromBody()> ByVal value As login)
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            Dim uid As String = value.uid
            Dim pwd As String = value.pwd

            s = "select iuid,icode from member where iuid = @iuid limit 1;"
            Dim rs As List(Of member) = conn.oConn.Query(Of member)(s, New With {.iuid = uid})

            If Not (rs.Count > 0) Then
                feedback.rid = -1
                feedback.message = "找不到該帳號"
                Return feedback
            End If

            If pwd <> rs(0).icode Then
                feedback.rid = -1
                feedback.message = "密碼錯誤"
                Return feedback
            End If

            Dim members As New List(Of member)
            Dim member As New member
            member.dlogin = Format(Now, "yyyy-MM-dd hh:mm:ss")
            member.iuid = uid
            members.Add(member)
            s = "update member set dlogin = @dlogin where iuid = @iuid;"
            conn.oConn.Execute(s, members)
            feedback.rid = 1
            feedback.message = "登入成功"
            Return feedback
        End Function

    End Class


    Public Class signupController
        Inherits ApiController

        ' POST: api/signup
        Public Function PostValue(<FromBody()> ByVal value As signup)
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            Dim uid As String = value.uid
            Dim pwd As String = value.pwd
            Dim name As String = value.name
            Dim sex As String = value.sex
            Dim birthday As String = String.Empty

            Try
                birthday = IIf(value.birthday.ToString.Trim = "", "", Format(CDate(value.birthday.ToString), "yyyy-MM-dd"))
            Catch ex As Exception
                birthday = ""
            End Try

            Dim phone As String = value.phone
            Dim email As String = value.email

            s = "select iuid,ephone from member where iuid = @iuid or ephone = @ephone;"
            Dim rs As List(Of member) = conn.oConn.Query(Of member)(s, New With {.iuid = uid, .ephone = phone})

            If rs.Count > 0 Then
                If rs.FindAll(Function(x) x.iuid = uid).Count > 0 Then
                    feedback.rid = -1
                    feedback.message = "該帳號已經被註冊"
                    Return feedback
                End If
                If rs.FindAll(Function(x) x.ephone = phone).Count > 0 Then
                    feedback.rid = -1
                    feedback.message = "該手機已經被註冊"
                    Return feedback
                End If
            End If

            Dim members As New List(Of member)
            Dim member As New member
            member.iuid = uid
            member.icode = pwd
            member.nname = name
            member.isex = sex
            member.birthday = birthday
            member.ephone = phone
            member.eemail = email
            member.qpoint = 0
            member.dlogin = Format(Now, "yyyy-MM-dd HH:mm:ss")
            members.Add(member)
            s = "INSERT INTO `project`.`member` (`iuid`, `icode`, `nname`, `isex`, `birthday`, `ephone`, `eemail`, `qpoint`, `dlogin`) VALUES (@iuid, @icode, @nname, @isex, @birthday, @ephone, @eemail, @qpoint, @dlogin);"
            conn.oConn.Execute(s, members)

            feedback.rid = 1
            feedback.message = "註冊成功"
            Return feedback

        End Function
    End Class

    Public Class viewLogController
        Inherits ApiController

        ' POST: api/viewLog
        Public Function PostValue(<FromBody()> ByVal value As view_log)
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            Dim iacc As String = value.iacc
            Dim ipd As String = value.ipd

            Dim view_logs As New List(Of view_log)
            Dim view_log As New view_log
            view_log.iacc = iacc
            view_log.ipd = ipd
            view_logs.Add(view_log)
            s = "INSERT INTO `project`.`view_log` (`iacc`, `ipd`) VALUES (@iacc, @ipd);"
            conn.oConn.Execute(s, view_logs)

            feedback.rid = 1
            feedback.message = "記錄成功"
            Return feedback
        End Function
    End Class
End Namespace

Public Class login
    Public Property uid As String
    Public Property pwd As String
End Class

Public Class signup
    Public Property uid As String
    Public Property pwd As String
    Public Property name As String
    Public Property sex As Integer
    Public Property birthday As String
    Public Property phone As String
    Public Property email As String
End Class

Public Class feedback
    Public Property rid As Integer
    Public Property message As String
End Class