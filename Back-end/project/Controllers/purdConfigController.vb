Imports System.Net
Imports System.Web.Http
Imports Dapper

Namespace Controllers
    Public Class purdTypeController
        Inherits ApiController

        ' GET: api/purdType
        Public Function GetValues()
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            s = "select itype,nname,ememo from purd_type;"
            Dim rs As List(Of purd_type) = conn.oConn.Query(Of purd_type)(s)
            If rs.Count > 0 Then
                Return rs
            Else
                feedback.rid = -1
                feedback.message = "查無資料"
                Return feedback
            End If
        End Function

        ' GET: api/purdType/5
        Public Function GetValue(ByVal itype As Integer)
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            s = "select itype,nname,ememo from purd_type where itype = @itype;"
            Dim rs As List(Of purd_type) = conn.oConn.Query(Of purd_type)(s, New With {.itype = itype})

            If rs.Count > 0 Then
                Return rs(0)
            Else
                feedback.rid = -1
                feedback.message = "查無資料"
                Return feedback
            End If
        End Function

        ' POST: api/purdType
        Public Function PostValue(<FromBody()> ByVal value As purd_type)
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            Dim nname As String = value.nname
            Dim ememo As String = value.ememo

            s = "select nname from purd_type where nname = @nname;"
            Dim rs As List(Of purd_type) = conn.oConn.Query(Of purd_type)(s, New With {.nname = nname})

            If rs.Count > 0 Then
                If rs.FindAll(Function(x) x.nname = nname).Count > 0 Then
                    feedback.rid = -1
                    feedback.message = "已存在"
                    Return feedback
                End If
            End If


            Dim purd_types As New List(Of purd_type)
            Dim purd_type As New purd_type
            purd_type.nname = nname
            purd_type.ememo = ememo
            purd_types.Add(purd_type)
            s = "INSERT INTO `project`.`purd_type` (`nname`, `ememo`) VALUES (@nname, @ememo);"
            conn.oConn.Execute(s, purd_types)

            feedback.rid = 1
            feedback.message = "新增成功"
            Return feedback
        End Function

        ' PUT: api/purdType/5
        Public Function PutValue(ByVal itype As String, <FromBody()> ByVal value As purd_type)

            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            Dim u_itype As String = value.itype
            Dim u_nname As String = value.nname
            Dim u_ememo As String = value.ememo

            s = "select itype from purd_type where itype = @itype;"
            Dim rs As List(Of purd_type) = conn.oConn.Query(Of purd_type)(s, New With {.itype = itype})

            If rs.Count < 1 Then
                If rs.FindAll(Function(x) x.itype = itype).Count < 1 Then
                    feedback.rid = -1
                    feedback.message = "不存在"
                    Return feedback
                End If
            End If

            Dim purd_types As New List(Of purd_type)
            Dim purd_type As New purd_type
            purd_type.nname = u_nname
            purd_type.ememo = u_ememo
            purd_types.Add(purd_type)
            s = String.Format("UPDATE `project`.`purd_type` set `nname` = @nname, `ememo` = @ememo WHERE itype = '{0}';", itype)
            conn.oConn.Execute(s, purd_types)

            feedback.rid = 1
            feedback.message = "更新成功"
            Return feedback
        End Function

        ' DELETE: api/purdType/5
        Public Function DeleteValue(ByVal itype As Integer)
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty

            s = "select itype from purd_type where itype = @itype;"
            Dim rs As List(Of purd_type) = conn.oConn.Query(Of purd_type)(s, New With {.itype = itype})

            If rs.Count < 1 Then
                If rs.FindAll(Function(x) x.itype = itype).Count < 1 Then
                    feedback.rid = -1
                    feedback.message = "不存在"
                    Return feedback
                End If
            End If

            Dim purd_types As New List(Of purd_type)
            Dim purd_type As New purd_type
            purd_type.itype = itype
            purd_types.Add(purd_type)
            s = "delete from `project`.`purd_type` where itype = @itype;"
            conn.oConn.Execute(s, purd_types)

            feedback.rid = 1
            feedback.message = "成功刪除"
            Return feedback
        End Function
    End Class

    Public Class purdUnitController
        Inherits ApiController

        ' GET: api/purdUnit
        Public Function GetValues()
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            s = "select iunit,nname,ememo from purd_unit;"
            Dim rs As List(Of purd_unit) = conn.oConn.Query(Of purd_unit)(s)
            If rs.Count > 0 Then
                Return rs
            Else
                feedback.rid = -1
                feedback.message = "查無資料"
                Return feedback
            End If
        End Function

        ' GET: api/purdUnit/5
        Public Function GetValue(ByVal iunit As Integer)
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            s = "select iunit,nname,ememo from purd_unit where iunit = @iunit;"
            Dim rs As List(Of purd_unit) = conn.oConn.Query(Of purd_unit)(s, New With {.iunit = iunit})

            If rs.Count > 0 Then
                Return rs(0)
            Else
                feedback.rid = -1
                feedback.message = "查無資料"
                Return feedback
            End If
        End Function

        ' POST: api/purdUnit
        Public Function PostValue(<FromBody()> ByVal value As purd_unit)
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            Dim nname As String = value.nname
            Dim ememo As String = value.ememo

            s = "select nname from purd_unit where nname = @nname;"
            Dim rs As List(Of purd_unit) = conn.oConn.Query(Of purd_unit)(s, New With {.nname = nname})

            If rs.Count > 0 Then
                If rs.FindAll(Function(x) x.nname = nname).Count > 0 Then
                    feedback.rid = -1
                    feedback.message = "已存在"
                    Return feedback
                End If
            End If


            Dim purd_units As New List(Of purd_unit)
            Dim purd_unit As New purd_unit
            purd_unit.nname = nname
            purd_unit.ememo = ememo
            purd_units.Add(purd_unit)
            s = "INSERT INTO `project`.`purd_unit` (`nname`, `ememo`) VALUES (@nname, @ememo);"
            conn.oConn.Execute(s, purd_units)

            feedback.rid = 1
            feedback.message = "新增成功"
            Return feedback
        End Function

        ' PUT: api/purdUnit/5
        Public Function PutValue(ByVal iunit As String, <FromBody()> ByVal value As purd_unit)

            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            Dim u_iunit As String = value.iunit
            Dim u_nname As String = value.nname
            Dim u_ememo As String = value.ememo

            s = "select iunit from purd_unit where iunit = @iunit;"
            Dim rs As List(Of purd_unit) = conn.oConn.Query(Of purd_unit)(s, New With {.iunit = iunit})

            If rs.Count < 1 Then
                If rs.FindAll(Function(x) x.iunit = iunit).Count < 1 Then
                    feedback.rid = -1
                    feedback.message = "不存在"
                    Return feedback
                End If
            End If

            Dim purd_units As New List(Of purd_unit)
            Dim purd_unit As New purd_unit
            purd_unit.nname = u_nname
            purd_unit.ememo = u_ememo
            purd_units.Add(purd_unit)
            s = String.Format("UPDATE `project`.`purd_unit` set `nname` = @nname, `ememo` = @ememo WHERE iunit = '{0}';", iunit)
            conn.oConn.Execute(s, purd_units)

            feedback.rid = 1
            feedback.message = "更新成功"
            Return feedback
        End Function

        ' DELETE: api/purdUnit/5
        Public Function DeleteValue(ByVal iunit As Integer)
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty

            s = "select iunit from purd_unit where iunit = @iunit;"
            Dim rs As List(Of purd_unit) = conn.oConn.Query(Of purd_unit)(s, New With {.iunit = iunit})

            If rs.Count < 1 Then
                If rs.FindAll(Function(x) x.iunit = iunit).Count < 1 Then
                    feedback.rid = -1
                    feedback.message = "不存在"
                    Return feedback
                End If
            End If

            Dim purd_units As New List(Of purd_unit)
            Dim purd_unit As New purd_unit
            purd_unit.iunit = iunit
            purd_units.Add(purd_unit)
            s = "delete from `project`.`purd_unit` where iunit = @iunit;"
            conn.oConn.Execute(s, purd_unit)

            feedback.rid = 1
            feedback.message = "成功刪除"
            Return feedback
        End Function
    End Class

    Public Class purdPicController
        Inherits ApiController

        ' GET: api/purdPic
        Public Function GetValues(ByVal ipd As String)
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            s = "select ipd,itype,eurl from purd_pic where ipd = @ipd;"
            Dim rs As List(Of purd_pic) = conn.oConn.Query(Of purd_pic)(s, New With {.ipd = ipd})

            If rs.Count > 0 Then
                Return rs
            Else
                feedback.rid = -1
                feedback.message = "查無資料"
                Return feedback
            End If
        End Function

        ' GET: api/purdPic/5
        Public Function GetValue(ByVal ipd As String, ByVal itype As String)
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            s = "select ipd,itype,eurl from purd_pic where ipd = @ipd and  itype = @itype;"
            Dim rs As List(Of purd_pic) = conn.oConn.Query(Of purd_pic)(s, New With {.ipd = ipd, .itype = itype})

            If rs.Count > 0 Then
                Return rs(0)
            Else
                feedback.rid = -1
                feedback.message = "查無資料"
                Return feedback
            End If
        End Function

        ' POST: api/purdPic
        Public Function PostValue(<FromBody()> ByVal value As purd_pic)
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            Dim ipd As String = value.ipd
            Dim itype As String = value.itype
            Dim eurl As String = value.eurl

            s = "select ipd,itype from purd_pic where ipd = @ipd and itype = @itype;"
            Dim rs As List(Of purd_pic) = conn.oConn.Query(Of purd_pic)(s, New With {.ipd = ipd, .itype = itype})

            If rs.Count > 0 Then
                If rs.FindAll(Function(x) x.ipd = ipd And x.itype = itype).Count > 0 Then
                    feedback.rid = -1
                    feedback.message = "已存在"
                    Return feedback
                End If
            End If


            Dim purd_pics As New List(Of purd_pic)
            Dim purd_pic As New purd_pic
            purd_pic.ipd = ipd
            purd_pic.itype = itype
            purd_pic.eurl = eurl
            purd_pics.Add(purd_pic)
            s = "INSERT INTO `project`.`purd_pic` (`ipd`, `itype`, `eurl`) VALUES (@ipd, @itype, @eurl);"
            conn.oConn.Execute(s, purd_pics)

            feedback.rid = 1
            feedback.message = "新增成功"
            Return feedback
        End Function

        ' PUT: api/purdPic/5
        Public Function PutValue(ByVal ipd As String, ByVal itype As String, <FromBody()> ByVal value As purd_pic)

            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            Dim u_ipd As String = value.ipd
            Dim u_itype As String = value.itype
            Dim u_eurl As String = value.eurl

            s = "select ipd,itype from purd_pic where ipd = @ipd and itype = @itype;"
            Dim rs As List(Of purd_pic) = conn.oConn.Query(Of purd_pic)(s, New With {.ipd = ipd, .itype = itype})

            If rs.Count < 1 Then
                If rs.FindAll(Function(x) x.ipd = ipd And x.itype = itype).Count < 1 Then
                    feedback.rid = -1
                    feedback.message = "不存在"
                    Return feedback
                End If
            End If

            Dim purd_pics As New List(Of purd_pic)
            Dim purd_pic As New purd_pic
            purd_pic.itype = u_itype
            purd_pic.eurl = u_eurl
            purd_pics.Add(purd_pic)
            s = String.Format("UPDATE `project`.`purd_pic` set `itype` = @itype, `eurl` = @eurl WHERE ipd = '{0}' AND itype = '{1}';", ipd, itype)
            conn.oConn.Execute(s, purd_pics)

            feedback.rid = 1
            feedback.message = "更新成功"
            Return feedback
        End Function

        ' DELETE: api/purdPic/5
        Public Function DeleteValue(ByVal ipd As String, ByVal itype As String)
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty

            s = "select ipd,itype from purd_pic where ipd = @ipd and itype = @itype;"
            Dim rs As List(Of purd_pic) = conn.oConn.Query(Of purd_pic)(s, New With {.ipd = ipd, .itype = itype})

            If rs.Count < 1 Then
                If rs.FindAll(Function(x) x.ipd = ipd).Count < 1 Then
                    feedback.rid = -1
                    feedback.message = "不存在"
                    Return feedback
                End If
            End If

            Dim purd_pics As New List(Of purd_pic)
            Dim purd_pic As New purd_pic
            purd_pic.ipd = ipd
            purd_pic.itype = itype
            purd_pics.Add(purd_pic)
            s = "delete from `project`.`purd_pic` where ipd = @ipd and itype = @itype;"
            conn.oConn.Execute(s, purd_pics)

            feedback.rid = 1
            feedback.message = "成功刪除"
            Return feedback
        End Function
    End Class

    Public Class purdCaptionController
        Inherits ApiController

        ' GET: api/purdCaption
        Public Function GetValues(ByVal ipd As String)
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            s = "select ipd,itype,ememo from purd_caption where ipd = @ipd;"
            Dim rs As List(Of purd_caption) = conn.oConn.Query(Of purd_caption)(s, New With {.ipd = ipd})

            If rs.Count > 0 Then
                Return rs
            Else
                feedback.rid = -1
                feedback.message = "查無資料"
                Return feedback
            End If
        End Function

        ' GET: api/purdCaption/5
        Public Function GetValue(ByVal ipd As String, ByVal itype As String)
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            s = "select ipd,itype,ememo from purd_caption where ipd = @ipd and  itype = @itype;"
            Dim rs As List(Of purd_caption) = conn.oConn.Query(Of purd_caption)(s, New With {.ipd = ipd, .itype = itype})

            If rs.Count > 0 Then
                Return rs(0)
            Else
                feedback.rid = -1
                feedback.message = "查無資料"
                Return feedback
            End If
        End Function

        ' POST: api/purdCaption
        Public Function PostValue(<FromBody()> ByVal value As purd_caption)
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            Dim ipd As String = value.ipd
            Dim itype As String = value.itype
            Dim ememo As String = value.ememo

            s = "select ipd,itype from purd_caption where ipd = @ipd and itype = @itype;"
            Dim rs As List(Of purd_pic) = conn.oConn.Query(Of purd_pic)(s, New With {.ipd = ipd, .itype = itype})

            If rs.Count > 0 Then
                If rs.FindAll(Function(x) x.ipd = ipd And x.itype = itype).Count > 0 Then
                    feedback.rid = -1
                    feedback.message = "已存在"
                    Return feedback
                End If
            End If


            Dim purd_captions As New List(Of purd_caption)
            Dim purd_caption As New purd_caption
            purd_caption.ipd = ipd
            purd_caption.itype = itype
            purd_caption.ememo = ememo
            purd_captions.Add(purd_caption)
            s = "INSERT INTO `project`.`purd_caption` (`ipd`, `itype`, `ememo`) VALUES (@ipd, @itype, @ememo);"
            conn.oConn.Execute(s, purd_captions)

            feedback.rid = 1
            feedback.message = "新增成功"
            Return feedback
        End Function

        ' PUT: api/purdCaption/5
        Public Function PutValue(ByVal ipd As String, ByVal itype As String, <FromBody()> ByVal value As purd_caption)

            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            Dim u_ipd As String = value.ipd
            Dim u_itype As String = value.itype
            Dim u_ememo As String = value.ememo

            s = "select ipd,itype from purd_caption where ipd = @ipd and itype = @itype;"
            Dim rs As List(Of purd_caption) = conn.oConn.Query(Of purd_caption)(s, New With {.ipd = ipd, .itype = itype})

            If rs.Count < 1 Then
                If rs.FindAll(Function(x) x.ipd = ipd And x.itype = itype).Count < 1 Then
                    feedback.rid = -1
                    feedback.message = "不存在"
                    Return feedback
                End If
            End If

            Dim purd_captions As New List(Of purd_caption)
            Dim purd_caption As New purd_caption
            purd_caption.itype = u_itype
            purd_caption.ememo = u_ememo
            purd_captions.Add(purd_caption)
            s = String.Format("UPDATE `project`.`purd_caption` set `itype` = @itype, `ememo` = @ememo WHERE ipd = '{0}' AND itype = '{1}';", ipd, itype)
            conn.oConn.Execute(s, purd_caption)

            feedback.rid = 1
            feedback.message = "更新成功"
            Return feedback
        End Function

        ' DELETE: api/purdCaption/5
        Public Function DeleteValue(ByVal ipd As String, ByVal itype As String)
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty

            s = "select ipd,itype from purd_caption where ipd = @ipd and itype = @itype;"
            Dim rs As List(Of purd_caption) = conn.oConn.Query(Of purd_caption)(s, New With {.ipd = ipd, .itype = itype})

            If rs.Count < 1 Then
                If rs.FindAll(Function(x) x.ipd = ipd).Count < 1 Then
                    feedback.rid = -1
                    feedback.message = "不存在"
                    Return feedback
                End If
            End If

            Dim purd_captions As New List(Of purd_caption)
            Dim purd_caption As New purd_caption
            purd_caption.ipd = ipd
            purd_caption.itype = itype
            purd_captions.Add(purd_caption)
            s = "delete from `project`.`purd_caption` where ipd = @ipd and itype = @itype;"
            conn.oConn.Execute(s, purd_captions)

            feedback.rid = 1
            feedback.message = "成功刪除"
            Return feedback
        End Function
    End Class
End Namespace