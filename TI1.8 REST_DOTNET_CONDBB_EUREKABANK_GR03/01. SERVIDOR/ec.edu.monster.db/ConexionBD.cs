using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data.SqlClient;
using System.Linq;
using System.Web;

namespace ec.edu.monster.db
{
    public class ConexionBD
    {
        private static readonly string connectionString =
            ConfigurationManager.ConnectionStrings["EurekaDB"].ConnectionString;

        public static SqlConnection ObtenerConexion()
        {
            SqlConnection cn = new SqlConnection(connectionString);
            try
            {
                cn.Open();
                return cn;
            }
            catch (SqlException ex)
            {
                throw new Exception("Error al conectar a la base de datos: " + ex.Message);
            }
        }
    }
}