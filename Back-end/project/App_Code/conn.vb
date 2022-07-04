Imports MySql.Data.MySqlClient

Public Class conn

    Public oConn As MySqlConnection

    Public oStatus As Boolean = False

    Private sConnStr As String

    Sub New(ByVal DB$)

        sConnStr = String.Format("server=localhost;user=root;database={0};port=3306;password=Project3306", DB)

        Try
            oConn = New MySqlConnection(sConnStr)
            oConn.Open()
            oConn.Close()
            oStatus = True
        Catch ex As Exception
            oStatus = False
        Finally
            oConn.Dispose()
        End Try
    End Sub

    Public Function execute(ByVal s$) As Boolean
        Dim mySqlCmd As MySqlCommand
        Try
            oConn.Open()
            mySqlCmd = New MySqlCommand(s, oConn)
            mySqlCmd.ExecuteReader()
            oConn.Close()
            Return True
        Catch ex As Exception
            Return False
        Finally
            oConn.Dispose()
        End Try
    End Function

    Public Function getDatatable(ByVal s$) As Data.DataTable
        Dim oDbAdapter As MySqlDataAdapter
        Dim oTable As New Data.DataTable
        Try
            oConn.Open()
            oDbAdapter = New MySqlDataAdapter(s, oConn)
            oDbAdapter.Fill(oTable)
            oConn.Close()
            Return oTable
        Catch ex As Exception
            Return New Data.DataTable
        Finally
            oConn.Dispose()
        End Try
    End Function

    Public Function getDataset(ByVal s$) As Data.DataSet
        Dim oDbAdapter As MySqlDataAdapter
        Dim oSet As New Data.DataSet
        Try
            oConn.Open()
            oDbAdapter = New MySqlDataAdapter(s, oConn)
            oDbAdapter.Fill(oSet)
            oConn.Close()
            Return oSet
        Catch ex As Exception
            Return New Data.DataSet
        Finally
            oConn.Dispose()
        End Try
    End Function

End Class
