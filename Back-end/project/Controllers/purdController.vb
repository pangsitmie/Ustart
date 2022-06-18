Imports System.Net
Imports System.Web.Http
Imports Dapper

Namespace Controllers
    Public Class purdTypeController
        Inherits ApiController

        ' POST: api/purdType
        Public Function PostValue(<FromBody()> ByVal value As purd_type)
            Dim feedback As New feedback
            Dim conn As conn = New conn("project")
            Dim s As String = String.Empty
            Dim itype As String = value.itype
            Dim nname As String = value.nname
            Dim ememo As String = value.ememo

            Return value
        End Function
    End Class

End Namespace