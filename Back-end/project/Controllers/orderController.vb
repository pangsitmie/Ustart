Imports System.Net
Imports System.Web.Http
Imports Dapper

Namespace Controllers

    Public Class orderDataController
        Inherits ApiController

        ' POST: api/orderData
        Public Function PostValue(<FromBody()> ByVal value As List(Of purdCar_get))
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            Dim dchgdate As String = Format(Now, "yyyy-MM-dd hh:mm:ss")

            If value.Count > 0 Then
                Dim order_datas As New List(Of order_data)
                Dim order_data As New order_data
                order_data.istatus = 1
                order_data.dchgdate = dchgdate
                order_datas.Add(order_data)
                s = "INSERT INTO `project`.`order_data` (`istatus`, `dchgdate`) VALUES (@istatus, @dchgdate);"
                conn.oConn.Execute(s, order_datas)

                s = "select a.iorder from order_data a left join order_detail b on a.iorder = b.iorder where b.iorder is null order by a.iorder desc limit 1;"
                Dim rs As List(Of order_data) = conn.oConn.Query(Of order_data)(s)
                If rs.Count > 0 Then
                    Dim iorder As String = rs(0).iorder
                    s = String.Format("select iacc from member where iuid = '{0}' limit 1", value(0).iuid)
                    Dim rs2 As List(Of member) = conn.oConn.Query(Of member)(s)
                    Dim order_details As New List(Of order_detail)
                    If rs2.Count > 0 Then
                        Dim iacc As String = rs2(0).iacc
                        Dim s_car As String = String.Empty
                        Dim purd_cars As New List(Of purd_car)
                        For Each obj As purdCar_get In value
                            Dim ipd As String = obj.ipd
                            Dim qquantity As String = obj.uqquantity
                            Dim qprice As String = obj.qprice
                            Dim order_detail As New order_detail
                            Dim purd_car As New purd_car
                            order_detail.iacc = iacc
                            order_detail.iorder = iorder
                            order_detail.ipd = ipd
                            order_detail.qquantity = qquantity
                            order_detail.qprice = qprice
                            order_details.Add(order_detail)
                            purd_car.id = obj.id
                            purd_cars.Add(purd_car)
                        Next
                        s = "INSERT INTO `project`.`order_detail` (`iacc`, `iorder`, `ipd`, `qquantity`, `qprice`) VALUES (@iacc, @iorder, @ipd, @qquantity, @qprice);"
                        s_car = "UPDATE `project`.`purd_car` set `istatus` = 'D' WHERE id = @id;"
                        conn.oConn.Execute(s, order_details)
                        conn.oConn.Execute(s_car, purd_cars)
                        feedback.rid = 1
                        feedback.message = "訂單新增成功"
                    Else
                        feedback.rid = -1
                        feedback.message = "找不到顧客資料"
                    End If
                Else
                    feedback.rid = -1
                    feedback.message = "找不到訂單資料"
                End If
            Else
                feedback.rid = -1
                feedback.message = "請輸入購物車資料"
            End If
            Return feedback
        End Function

        ' DELETE: api/orderData/5
        Public Function DeleteValue(ByVal iorder As String)
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            Dim dchgdate As String = Format(Now, "yyyy-MM-dd hh:mm:ss")
            s = "select * from order_data where iorder = @iorder and istatus <> 0"
            Dim rs As List(Of order_data) = conn.oConn.Query(Of order_data)(s, New With {.iorder = iorder})

            If rs.Count > 0 Then
                Dim order_data As New order_data
                Dim order_datas As New List(Of order_data)
                order_data.iorder = iorder
                order_data.dchgdate = dchgdate
                order_datas.Add(order_data)
                s = "update `project`.`order_data` set istatus = 0, dchgdate = @dchgdate where iorder = @iorder;"
                conn.oConn.Execute(s, order_datas)
                feedback.rid = 1
                feedback.message = "刪除訂單成功"
            Else
                feedback.rid = -1
                feedback.message = "找不到訂單資料"
            End If
            Return feedback
        End Function
    End Class

    Public Class purdCarController
        Inherits ApiController

        ' GET: api/purdCar
        Public Function GetValues(ByVal iuid As String)
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            Dim iacc As String = String.Empty

            s = "select * from member where iuid = @iuid"
            Dim rs0 As List(Of member) = conn.oConn.Query(Of member)(s, New With {.iuid = iuid})

            If rs0.Count > 0 Then
                iacc = rs0(0).iacc
            Else
                feedback.rid = -1
                feedback.message = "找不到該用戶"
                Return feedback
            End If

            s = "select a.id,b.iuid,a.ipd,a.qquantity as uqquantity,c.qprice * a.qquantity as uqprice " _
              & ",c.nname,c.qprice,c.qquantity,c.itype,d.iunit,d.nname as nunit,c.dindate,c.dlinedate " _
              & "from purd_car a " _
              & "left join member b on a.iacc = b.iacc " _
              & "left join purd_data c on a.ipd = c.ipd " _
              & "left join purd_unit d on c.iunit = d.iunit " _
              & "where a.iacc = @iacc and a.istatus = 'T' and c.istatus = 'T';"
            Dim rs As List(Of purdCar_get) = conn.oConn.Query(Of purdCar_get)(s, New With {.iacc = iacc})
            If rs.Count > 0 Then
                Dim types As New List(Of String)
                For Each x In rs
                    Dim type = x.itype.Split(",")
                    If type.Length > 0 Then
                        For Each t In type
                            If types.FindAll(Function(y) y = t).Count < 1 Then
                                types.Add(t)
                            End If
                        Next
                    End If
                Next

                Dim itype As String = "("

                For Each t In types
                    itype &= t & ","
                Next
                itype = Left(itype, Len(itype) - 1) & ")"

                s = String.Format("select * from purd_type where itype in {0};", itype)
                Dim dt As DataTable = conn.getDatatable(s)


                For Each x In rs
                    Dim findrow As DataRow() = dt.Select("itype in (" & x.itype & ")")
                    If findrow.Count > 0 Then
                        x.ntype = String.Empty
                        For Each r In findrow
                            x.ntype &= r.Item("nname").ToString.Trim & ","
                        Next
                        x.ntype = Left(x.ntype, Len(x.ntype) - 1)
                    End If
                Next
                Return rs
            Else
                feedback.rid = -1
                feedback.message = "查無資料"
                Return feedback
            End If
        End Function

        ' GET: api/purdCar/5
        Public Function GetValue(ByVal id As String)
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            s = "select a.id,b.iuid,a.ipd,a.qquantity as uqquantity,c.qprice * a.qquantity as uqprice " _
              & ",c.nname,c.qprice,c.qquantity,c.itype,d.iunit,d.nname as nunit,c.dindate,c.dlinedate " _
              & "from purd_car a " _
              & "left join member b on a.iacc = b.iacc " _
              & "left join purd_data c on a.ipd = c.ipd " _
              & "left join purd_unit d on c.iunit = d.iunit " _
              & "where a.id = @id and a.istatus = 'T' and c.istatus = 'T';"
            Dim rs As List(Of purdCar_get) = conn.oConn.Query(Of purdCar_get)(s, New With {.id = id})
            If rs.Count > 0 Then
                Dim types As New List(Of String)
                For Each x In rs
                    Dim type = x.itype.Split(",")
                    If type.Length > 0 Then
                        For Each t In type
                            If types.FindAll(Function(y) y = t).Count < 1 Then
                                types.Add(t)
                            End If
                        Next
                    End If
                Next

                Dim itype As String = "("

                For Each t In types
                    itype &= t & ","
                Next
                itype = Left(itype, Len(itype) - 1) & ")"

                s = String.Format("select * from purd_type where itype in {0};", itype)
                Dim dt As DataTable = conn.getDatatable(s)


                For Each x In rs
                    Dim findrow As DataRow() = dt.Select("itype in (" & x.itype & ")")
                    If findrow.Count > 0 Then
                        x.ntype = String.Empty
                        For Each r In findrow
                            x.ntype &= r.Item("nname").ToString.Trim & ","
                        Next
                        x.ntype = Left(x.ntype, Len(x.ntype) - 1)
                    End If
                Next
                Return rs(0)
            Else
                feedback.rid = -1
                feedback.message = "查無資料"
                Return feedback
            End If
        End Function

        ' POST: api/purdCar
        Public Function PostValue(<FromBody()> ByVal value As purdCar)
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            Dim iuid As String = value.iuid
            Dim iacc As String = String.Empty
            Dim ipd As String = value.ipd
            Dim qquantity As String = value.qquantity

            s = "select * from member where iuid = @iuid"
            Dim rs As List(Of member) = conn.oConn.Query(Of member)(s, New With {.iuid = iuid})

            If rs.Count > 0 Then
                iacc = rs(0).iacc
            Else
                feedback.rid = -1
                feedback.message = "找不到該用戶"
                Return feedback
            End If

            Dim purd_cars As New List(Of purd_car)
            Dim purd_car As New purd_car
            purd_car.iacc = iacc
            purd_car.ipd = ipd
            purd_car.qquantity = qquantity
            purd_cars.Add(purd_car)
            s = "INSERT INTO `project`.`purd_car` (`iacc`, `ipd`, `qquantity`) VALUES (@iacc, @ipd, @qquantity);"
            conn.oConn.Execute(s, purd_cars)

            feedback.rid = 1
            feedback.message = "新增成功"
            Return feedback

        End Function

        ' PUT: api/purdCar/5
        Public Function PutValue(ByVal id As String, <FromBody()> ByVal value As purd_car)

            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            Dim u_ipd As String = value.ipd
            Dim u_qquantity As String = value.qquantity
            Dim u_dchgdate As String = Format(Now, "yyyy-MM-dd HH:mm:ss")

            s = "select id from purd_car where id = @id and istatus = 'T';"
            Dim rs As List(Of purd_car) = conn.oConn.Query(Of purd_car)(s, New With {.id = id})

            If rs.Count < 1 Then
                If rs.FindAll(Function(x) x.id = id).Count < 1 Then
                    feedback.rid = -1
                    feedback.message = "不存在"
                    Return feedback
                End If
            End If

            Dim purd_cars As New List(Of purd_car)
            Dim purd_car As New purd_car
            purd_car.ipd = u_ipd
            purd_car.qquantity = u_qquantity
            purd_cars.Add(purd_car)
            s = String.Format("UPDATE `project`.`purd_car` set `ipd` = @ipd, `qquantity` = @qquantity, `dchgdate` = '{0}' WHERE id = '{1}' AND istatus = 'T';", u_dchgdate, id)
            conn.oConn.Execute(s, purd_cars)

            feedback.rid = 1
            feedback.message = "更新成功"
            Return feedback
        End Function

        ' DELETE: api/purdCar/5
        Public Function DeleteValue(ByVal id As String)
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            Dim dchgdate As String = Format(Now, "yyyy-MM-dd HH:mm:ss")

            s = "select id from purd_car where id = @id and istatus <> 'D';"
            Dim rs As List(Of purd_car) = conn.oConn.Query(Of purd_car)(s, New With {.id = id})

            If rs.Count < 1 Then
                If rs.FindAll(Function(x) x.id = id).Count < 1 Then
                    feedback.rid = -1
                    feedback.message = "不存在"
                    Return feedback
                End If
            End If

            Dim purd_cars As New List(Of purd_car)
            Dim purd_car As New purd_car
            purd_car.id = id
            purd_cars.Add(purd_car)
            s = String.Format("update `project`.`purd_car` set istatus = 'D', dchgdate = '{0}' where id = @id;", dchgdate)
            conn.oConn.Execute(s, purd_cars)

            feedback.rid = 1
            feedback.message = "成功刪除"
            Return feedback
        End Function
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

Public Class purdCar
    Public Property id As String
    Public Property iuid As String
    Public Property ipd As String
    Public Property qquantity As Integer
End Class

Public Class purdCar_get
    Public Property id As String
    Public Property iuid As String
    Public Property ipd As String
    Public Property uqquantity As Integer
    Public Property uqprice As String
    Public Property nname As String
    Public Property qprice As String
    Public Property qquantity As Integer
    Public Property itype As String
    Public Property ntype As String
    Public Property iunit As String
    Public Property nunit As String
    Public Property dindate As String
    Public Property dlinedate As String
End Class