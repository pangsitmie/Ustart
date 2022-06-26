Imports System.Net
Imports System.Web.Http
Imports Dapper

Namespace Controllers
    Public Class orderController
        Inherits ApiController
    End Class

    Public Class purdSearchController
        Inherits ApiController

        ' POST: api/purdSearch
        Public Function PostValue(<FromBody()> ByVal value As purdSearch)
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty

            If value Is Nothing Then
                feedback.rid = -1
                feedback.message = "請傳入參數"
                Return feedback
            End If

            Dim ipd As String = value.ipd
            Dim ivender As String = value.ivender
            Dim nname As String = value.nname
            Dim qprice As String = value.qprice
            Dim itype As String = value.itype
            Dim dindate As String = value.dindate
            Dim dlinedate As String = value.dlinedate

            s = "select `ipd`, `ivender`, `nname`, `qprice`, `qquantity`, `itype`, `iunit`, `dindate`, `dlinedate`, `dfinalprice` from purd_data "
            s &= "where 1 = 1 "
            If Len(ipd) > 0 Then
                s &= "and ipd = @ipd "
            End If
            If Len(ivender) > 0 Then
                s &= "and ivender = @ivender "
            End If
            If Len(nname) > 0 Then
                nname = String.Format("%{0}%", nname)
                s &= "and nname like @nname "
            End If
            If Len(qprice) > 0 Then
                Try
                    If qprice.IndexOf(",") > -1 Then
                        Dim tempArr = qprice.Split(",")
                        Dim p1 As Double = CType(tempArr(0), Double)
                        Dim p2 As Double = CType(tempArr(1), Double)
                        s &= String.Format("and qprice between '{0}' and '{1}' ", p1, p2)
                    Else
                        qprice = CType(qprice, Double)
                        s &= "and qprice = @qprice "
                    End If
                Catch ex As Exception
                End Try
            End If
            If Len(itype) > 0 Then
                Try
                    If itype.IndexOf(",") > -1 Then
                        Dim tempArr = itype.Split(",")
                        Dim tempS As String = String.Empty
                        For Each str As String In tempArr
                            Dim tempStr As Integer
                            tempStr = CType(str, Integer)
                            tempS &= String.Format("itype = '{0}' or itype like '{0},%' or itype like '%,{0},%' or itype like '%,{0}' ", tempStr)
                            tempS &= "or "
                        Next
                        tempS = Left(tempS, Len(tempS) - 3)
                        s &= String.Format("and ({0}) ", tempS)
                    Else
                        Dim tempStr As Integer
                        tempStr = CType(itype, Integer)
                        s &= String.Format("and (itype = '{0}' or itype like '{0},%' or itype like '%,{0},%' or itype like '%,{0}') ", tempStr)
                    End If
                Catch ex As Exception
                End Try
            End If
            If Len(dindate) > 0 Then
                Try
                    If dindate.IndexOf(",") > -1 Then
                        Dim tempArr = dindate.Split(",")
                        Dim d1 As String = Format(CType(tempArr(0), Date), "yyyy-MM-dd HH:mm:ss")
                        Dim d2 As String = Format(CType(tempArr(1), Date), "yyyy-MM-dd HH:mm:ss")
                        s &= String.Format("and dindate between '{0}' and '{1}' ", d1, d2)
                    Else
                        dindate = Format(CType(dindate, Date), "yyyy-MM-dd HH:mm:ss")
                        s &= "and dindate = @dindate "
                    End If
                Catch ex As Exception
                End Try
            End If
            If Len(dlinedate) > 0 Then
                Try
                    If dlinedate.IndexOf(",") > -1 Then
                        Dim tempArr = dlinedate.Split(",")
                        Dim d1 As String = Format(CType(tempArr(0), Date), "yyyy-MM-dd HH:mm:ss")
                        Dim d2 As String = Format(CType(tempArr(1), Date), "yyyy-MM-dd HH:mm:ss")
                        s &= String.Format("and dlinedate between '{0}' and '{1}' ", d1, d2)
                    Else
                        dlinedate = Format(CType(dlinedate, Date), "yyyy-MM-dd HH:mm:ss")
                        s &= "and dlinedate = @dlinedate "
                    End If
                Catch ex As Exception
                End Try
            End If
            s &= "and istatus <> 'D';"
            Dim rs As List(Of purdSearch) = conn.oConn.Query(Of purdSearch)(s, New With {.ipd = ipd,
                                                                                       .ivender = ivender,
                                                                                       .nname = nname,
                                                                                       .qprice = qprice,
                                                                                       .itype = itype,
                                                                                       .dindate = dindate,
                                                                                       .dlinedate = dlinedate})

            If rs.Count > 0 Then
                Return rs
            Else
                feedback.rid = -1
                feedback.message = "查無資料"
                Return feedback
            End If
        End Function
    End Class
End Namespace

Public Class purdSearch
    Public Property ipd As String
    Public Property ivender As String
    Public Property nname As String
    Public Property qprice As String
    Public Property qquantity As String
    Public Property itype As String
    Public Property iunit As String
    Public Property dindate As String
    Public Property dlinedate As String
    Public Property dfinalprice As String
End Class