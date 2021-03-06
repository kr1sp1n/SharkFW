
import java.util.Enumeration;
import net.sharkfw.knowledgeBase.SemanticTag;
import net.sharkfw.knowledgeBase.SharkCS;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import net.sharkfw.knowledgeBase.SharkCSAlgebra;
import net.sharkfw.knowledgeBase.SharkKBException;
import net.sharkfw.knowledgeBase.SpatialSTSet;
import net.sharkfw.knowledgeBase.SpatialSemanticTag;
import net.sharkfw.knowledgeBase.geom.SharkGeometry;
import net.sharkfw.knowledgeBase.geom.inmemory.InMemoSharkGeometry;
import net.sharkfw.knowledgeBase.inmemory.InMemoSharkKB;

/**
 *
 * @author s0542709, s0542541
 * @version 1.0
 *
 * This class provides some tests for SharkGeometry which uses the JTS-Library.
 * Hint: WKT = Well-known text; SRS = Spatial Reference System; SRID = Spatial
 * Reference System Identifier; SRS == SRID; WKT with SRS/SRID = Extended
 * Well-Known Text (EWKT);
 */
public class JTSMain {

    static net.sharkfw.knowledgeBase.geom.SpatialAlgebra usedFunctionClass;

    //Spatial Reference System: 4326 == WGS84
    static String srs = "4326";
    int srs_int = Integer.parseInt(srs);
    static String string_Point_Berlin_HTW_WH_G, string_Point_Berlin_HTW_TA,
            string_LineString_Berlin_HTW_WH_G, string_LineString_Berlin_HTW_WH_Complete,
            string_Polygon_Berlin_HTW_WH_G, string_Polygon_Berlin_HTW_WH_Complete,
            string_Polygon_Berlin_HTW_WH_Complete_WITHOUT_G, string_Polygon_Berlin_HTW_TA_Complete,
            string_Multipoint_Berlin_HTW_WH_G,
            string_Multipoint_Berlin_HTW_WH_Complete, string_Multilinestring_Berlin_HTW_WH_G,
            string_Multilinestring_Berlin_HTW_WH_Complete, string_Multipolygon_Berlin_HTW_Complete,
            string_Multipolygon_Berlin_HTW_WH_Complete,
            string_Multipolygon_Berlin_HTW_Complete_WITHOUT_WH_G, string_Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON,
            string_Geometrycollection_Berlin_HTW_WH_Complete_With_MULTIPOLYGON,
            string_Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON_WITHOUT_WH_G;

    static SharkGeometry Point_Berlin_HTW_WH_G, Point_Berlin_HTW_WH_G_EWKT,
            Point_Berlin_HTW_TA,
            LineString_Berlin_HTW_WH_G, LineString_Berlin_HTW_WH_G_EWKT,
            LineString_Berlin_HTW_WH_Complete, LineString_Berlin_HTW_WH_Complete_EWKT,
            Polygon_Berlin_HTW_WH_G, Polygon_Berlin_HTW_WH_G_EWKT, Polygon_Berlin_HTW_TA_Complete,
            Polygon_Berlin_HTW_WH_Complete, Polygon_Berlin_HTW_WH_Complete_EWKT,
            Polygon_Berlin_HTW_WH_Complete_WITHOUT_G, Polygon_Berlin_HTW_WH_Complete_WITHOUT_G_EWKT,
            Multipoint_Berlin_HTW_WH_G, Multipoint_Berlin_HTW_WH_G_EWKT, Multipoint_Berlin_HTW_WH_Complete,
            Multilinestring_Berlin_HTW_WH_G, Multilinestring_Berlin_HTW_WH_G_EWKT,
            Multilinestring_Berlin_HTW_WH_Complete, Multilinestring_Berlin_HTW_WH_Complete_EWKT,
            Multipolygon_Berlin_HTW_WH_Complete,
            Multipolygon_Berlin_HTW_Complete, Multipolygon_Berlin_HTW_Complete_WITHOUT_WH_G,
            Multipolygon_Berlin_HTW_Complete_EWKT, Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON,
            Geometrycollection_Berlin_HTW_WH_Complete_With_MULTIPOLYGON,
            Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON_EWKT,
            Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON_WITHOUT_WH_G;

    @BeforeClass
    public static void setUpClass() throws SharkKBException {
        usedFunctionClass = SharkCSAlgebra.getSpatialAlgebra();

        //Geometries with geographical coordinates 
        string_Point_Berlin_HTW_WH_G = "POINT (52.45606650054853 13.523988202214241)";
        string_Point_Berlin_HTW_TA = "POINT (52.49363039643433 13.522881120443344)";
        string_LineString_Berlin_HTW_WH_G = "LINESTRING (52.45606650054853 13.523988202214241, 52.45549525426796 13.525406420230865, "
                + "52.45515160397337 13.525016158819199, 52.45582827785886 13.523717299103737, 52.45606650054853 13.523988202214241)";
        string_LineString_Berlin_HTW_WH_Complete = "LINESTRING (52.45860862010212 13.526121228933334, 52.45684499901483 13.528932183980942,"
                + "52.45441290345384 13.526121228933334, 52.45586433148305 13.523031324148178, 52.45860862010212 13.526121228933334, "
                + "52.45606650054853 13.523988202214241, 52.45549525426796 13.525406420230865, 52.45515160397337 13.525016158819199, "
                + "52.45582827785886 13.523717299103737, 52.45606650054853 13.523988202214241)";
        string_Polygon_Berlin_HTW_WH_G = "POLYGON((52.45606650054853 13.523988202214241, 52.45549525426796 13.525406420230865,"
                + "52.45515160397337 13.525016158819199, 52.45582827785886 13.523717299103737, 52.45606650054853 13.523988202214241))";
        string_Polygon_Berlin_HTW_WH_Complete = "POLYGON((52.45860862010212 13.526121228933334, 52.45684499901483 13.528932183980942, "
                + "52.45441290345384 13.526121228933334, 52.45586433148305 13.523031324148178, 52.45860862010212 13.526121228933334))";
        string_Polygon_Berlin_HTW_TA_Complete = "POLYGON((52.49363039643433 13.522881120443344, 52.49429668066099 13.525370210409164, "
                + "52.49203649908915 13.526872247457504, 52.49220634335951 13.522559255361557, 52.49363039643433 13.522881120443344))";
        string_Polygon_Berlin_HTW_WH_Complete_WITHOUT_G = "POLYGON ((52.45860862010212 13.526121228933334, "
                + "52.45684499901483 13.528932183980942, 52.45441290345384 13.526121228933334, 52.45586433148305 13.523031324148178, "
                + "52.45860862010212 13.526121228933334), (52.45606650054853 13.523988202214241, 52.45549525426796 13.525406420230865, "
                + "52.45515160397337 13.525016158819199, 52.45582827785886 13.523717299103737, 52.45606650054853 13.523988202214241))";
        string_Multipoint_Berlin_HTW_WH_G = "MULTIPOINT ((52.45606650054853 13.523988202214241), (52.45549525426796 13.525406420230865),"
                + "(52.45515160397337 13.525016158819199), (52.45582827785886 13.523717299103737))";
        string_Multipoint_Berlin_HTW_WH_Complete = "MULTIPOINT (52.45860862010212 13.526121228933334, 52.45684499901483 13.528932183980942, "
                + "52.45441290345384 13.526121228933334, 52.45586433148305 13.523031324148178, 52.45606650054853 13.523988202214241, "
                + "52.45549525426796 13.525406420230865, 52.45515160397337 13.525016158819199, 52.45582827785886 13.523717299103737,"
                + "52.45606650054853 13.523988202214241, 52.45549525426796 13.525406420230865, 52.45515160397337 13.525016158819199, "
                + "52.45582827785886 13.523717299103737)";
        string_Multilinestring_Berlin_HTW_WH_G = "MULTILINESTRING ((52.45549525426796 13.525406420230865, 52.45606650054853 13.523988202214241,"
                + "52.45582827785886 13.523717299103737, 52.45515160397337 13.525016158819199, 52.45549525426796 13.525406420230865))";
        string_Multilinestring_Berlin_HTW_WH_Complete = "MULTILINESTRING ((52.45860862010212 13.526121228933334, "
                + "52.45684499901483 13.528932183980942, 52.45441290345384 13.526121228933334, 52.45586433148305 13.523031324148178, "
                + "52.45860862010212 13.526121228933334), (52.45606650054853 13.523988202214241, 52.45549525426796 13.525406420230865, "
                + "52.45515160397337 13.525016158819199, 52.45582827785886 13.523717299103737, 52.45606650054853 13.523988202214241))";
        string_Multipolygon_Berlin_HTW_WH_Complete = "MULTIPOLYGON (((52.45684499901483 13.528932183980942, 52.457726809558475 13.527526706457138, "
                + "52.45513861746845 13.524576276540756, 52.45441290345384 13.526121228933334, 52.45684499901483 13.528932183980942)), "
                + "((52.457726809558475 13.527526706457138, 52.45513861746845 13.524576276540756, 52.45586433148305 13.523031324148178, "
                + "52.45860862010212 13.526121228933334, 52.457726809558475 13.527526706457138)))";
        string_Multipolygon_Berlin_HTW_Complete = "MULTIPOLYGON (((52.45860862010212 13.526121228933334, 52.45684499901483 13.528932183980942, "
                + "52.45441290345384 13.526121228933334, 52.45586433148305 13.523031324148178, 52.45860862010212 13.526121228933334)),"
                + "((52.49363039643433 13.522881120443344, 52.49429668066099 13.525370210409164, "
                + "52.49203649908915 13.526872247457504, 52.49220634335951 13.522559255361557, 52.49363039643433 13.522881120443344)))";
        string_Multipolygon_Berlin_HTW_Complete_WITHOUT_WH_G = "MULTIPOLYGON (((52.49363039643433 13.522881120443344, "
                + "52.49429668066099 13.525370210409164, 52.49203649908915 13.526872247457504, 52.49220634335951 13.522559255361557, "
                + "52.49363039643433 13.522881120443344)),"
                + "((52.45860862010212 13.526121228933334, 52.45684499901483 13.528932183980942, 52.45441290345384 13.526121228933334, "
                + "52.45586433148305 13.523031324148178, 52.45860862010212 13.526121228933334),"
                + "(52.45606650054853 13.523988202214241, 52.45549525426796 13.525406420230865, "
                + "52.45515160397337 13.525016158819199, 52.45582827785886 13.523717299103737, 52.45606650054853 13.523988202214241)))";
        string_Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON = "GEOMETRYCOLLECTION(" + string_Multipolygon_Berlin_HTW_Complete + ")";
        string_Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON_WITHOUT_WH_G = "GEOMETRYCOLLECTION(" + string_Multipolygon_Berlin_HTW_Complete_WITHOUT_WH_G + ")";
        string_Geometrycollection_Berlin_HTW_WH_Complete_With_MULTIPOLYGON = "GEOMETRYCOLLECTION(" + string_Multipolygon_Berlin_HTW_WH_Complete + ")";

        Point_Berlin_HTW_WH_G = InMemoSharkGeometry.createGeomByWKT(string_Point_Berlin_HTW_WH_G);
        Point_Berlin_HTW_WH_G_EWKT = InMemoSharkGeometry.createGeomByEWKT("SRID=" + srs + "; " + string_Point_Berlin_HTW_WH_G);
        Point_Berlin_HTW_TA = InMemoSharkGeometry.createGeomByWKT(string_Point_Berlin_HTW_TA);
        LineString_Berlin_HTW_WH_G = InMemoSharkGeometry.createGeomByWKT(string_LineString_Berlin_HTW_WH_G);
        LineString_Berlin_HTW_WH_G_EWKT = InMemoSharkGeometry.createGeomByEWKT("SRID=" + srs + "; " + string_LineString_Berlin_HTW_WH_G);
        LineString_Berlin_HTW_WH_Complete = InMemoSharkGeometry.createGeomByWKT(string_LineString_Berlin_HTW_WH_Complete);
        LineString_Berlin_HTW_WH_Complete_EWKT = InMemoSharkGeometry.createGeomByEWKT(string_LineString_Berlin_HTW_WH_Complete + "; SRID=" + srs);
        Polygon_Berlin_HTW_WH_G = InMemoSharkGeometry.createGeomByWKT(string_Polygon_Berlin_HTW_WH_G);
        Polygon_Berlin_HTW_WH_G_EWKT = InMemoSharkGeometry.createGeomByEWKT("SRID=" + srs + "; " + string_Polygon_Berlin_HTW_WH_G);
        Polygon_Berlin_HTW_WH_Complete = InMemoSharkGeometry.createGeomByWKT(string_Polygon_Berlin_HTW_WH_Complete);
        Polygon_Berlin_HTW_WH_Complete_EWKT = InMemoSharkGeometry.createGeomByEWKT(string_Polygon_Berlin_HTW_WH_Complete + "; SRID=" + srs);
        Polygon_Berlin_HTW_TA_Complete = InMemoSharkGeometry.createGeomByWKT(string_Polygon_Berlin_HTW_TA_Complete);
        Polygon_Berlin_HTW_WH_Complete_WITHOUT_G = InMemoSharkGeometry.createGeomByWKT(string_Polygon_Berlin_HTW_WH_Complete_WITHOUT_G);
        Polygon_Berlin_HTW_WH_Complete_WITHOUT_G_EWKT = InMemoSharkGeometry.createGeomByEWKT(string_Polygon_Berlin_HTW_WH_Complete_WITHOUT_G + "; SRID=" + srs);
        Multipoint_Berlin_HTW_WH_G = InMemoSharkGeometry.createGeomByWKT(string_Multipoint_Berlin_HTW_WH_G);
        Multipoint_Berlin_HTW_WH_G_EWKT = InMemoSharkGeometry.createGeomByEWKT("SRID=" + srs + "; " + string_Multipoint_Berlin_HTW_WH_G);
        Multipoint_Berlin_HTW_WH_Complete = InMemoSharkGeometry.createGeomByWKT(string_Multipoint_Berlin_HTW_WH_Complete);
        Multilinestring_Berlin_HTW_WH_G = InMemoSharkGeometry.createGeomByWKT(string_Multilinestring_Berlin_HTW_WH_G);
        Multilinestring_Berlin_HTW_WH_G_EWKT = InMemoSharkGeometry.createGeomByEWKT(string_Multilinestring_Berlin_HTW_WH_G + "; SRID=" + srs);
        Multilinestring_Berlin_HTW_WH_Complete = InMemoSharkGeometry.createGeomByWKT(string_Multilinestring_Berlin_HTW_WH_Complete);
        Multilinestring_Berlin_HTW_WH_Complete_EWKT = InMemoSharkGeometry.createGeomByEWKT(string_Multilinestring_Berlin_HTW_WH_Complete + "; SRID=" + srs);
        Multipolygon_Berlin_HTW_WH_Complete = InMemoSharkGeometry.createGeomByWKT(string_Multipolygon_Berlin_HTW_WH_Complete);
        Multipolygon_Berlin_HTW_Complete = InMemoSharkGeometry.createGeomByWKT(string_Multipolygon_Berlin_HTW_Complete);
        Multipolygon_Berlin_HTW_Complete_EWKT = InMemoSharkGeometry.createGeomByEWKT("SRID=" + srs + "; " + string_Multipolygon_Berlin_HTW_Complete);
        Multipolygon_Berlin_HTW_Complete_WITHOUT_WH_G = InMemoSharkGeometry.createGeomByWKT(string_Multipolygon_Berlin_HTW_Complete_WITHOUT_WH_G);
        Geometrycollection_Berlin_HTW_WH_Complete_With_MULTIPOLYGON = InMemoSharkGeometry.createGeomByWKT(string_Geometrycollection_Berlin_HTW_WH_Complete_With_MULTIPOLYGON);
        Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON = InMemoSharkGeometry.createGeomByWKT(string_Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON);
        Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON_EWKT = InMemoSharkGeometry.createGeomByEWKT(string_Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON + "; SRID=" + srs);
        Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON_WITHOUT_WH_G = InMemoSharkGeometry.createGeomByWKT(string_Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON_WITHOUT_WH_G);
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /*
     * check created geometries ->
     */
    @Test
    public void create_test_AnyTag() throws SharkKBException {
        SemanticTag anyTag = InMemoSharkKB.createInMemoSemanticTag("anyTag", SharkCS.ANYURL);
        //SpatialSTSet anySTSet = InMemoSharkKB.createInMemoSpatialSemanticTag("anySTTag", , Point_Berlin_HTW_TA);
        //Assert.assertEquals(null, any);
        //Assert.assertEquals(true, usedFunctionClass.identical(any, any));
        //Assert.assertEquals(true, usedFunctionClass.isIn(any, any));
    }

    @Test
    public void createdSharkGeometryByGeographicalWKT_Point() throws SharkKBException {
        Assert.assertEquals(string_Point_Berlin_HTW_WH_G, Point_Berlin_HTW_WH_G.getWKT());
    }

    @Test
    public void createdSharkGeometryByGeographicalEWKT_Point() throws SharkKBException {
        Assert.assertEquals(string_Point_Berlin_HTW_WH_G, Point_Berlin_HTW_WH_G_EWKT.getWKT());
        Assert.assertEquals(srs_int, Point_Berlin_HTW_WH_G_EWKT.getSRS());
    }

    @Test
    public void createdSharkGeometryByGeographicalWKT_LINESTRING() throws SharkKBException {
        Assert.assertEquals(string_LineString_Berlin_HTW_WH_G, LineString_Berlin_HTW_WH_G.getWKT());
    }

    @Test
    public void createdSharkGeometryByGeographicalEWKT_LINESTRING() throws SharkKBException {
        Assert.assertEquals(string_LineString_Berlin_HTW_WH_G, LineString_Berlin_HTW_WH_G_EWKT.getWKT());
        Assert.assertEquals(srs_int, LineString_Berlin_HTW_WH_G_EWKT.getSRS());
    }

    @Test
    public void createdSharkGeometryByGeographicalWKT_LINESTRING_2() throws SharkKBException {
        Assert.assertEquals(string_LineString_Berlin_HTW_WH_Complete, LineString_Berlin_HTW_WH_Complete.getWKT());
    }

    @Test
    public void createdSharkGeometryByGeographicalEWKT_LINESTRING_2() throws SharkKBException {
        Assert.assertEquals(string_LineString_Berlin_HTW_WH_Complete, LineString_Berlin_HTW_WH_Complete_EWKT.getWKT());
        Assert.assertEquals(srs_int, LineString_Berlin_HTW_WH_Complete_EWKT.getSRS());
    }

    @Test
    public void createdSharkGeometryByGeographicalWKT_POLYGON() throws SharkKBException {
        Assert.assertEquals(string_Polygon_Berlin_HTW_WH_G, Polygon_Berlin_HTW_WH_G.getWKT());
    }

    @Test
    public void createdSharkGeometryByGeographicalEWKT_POLYGON() throws SharkKBException {
        Assert.assertEquals(string_Polygon_Berlin_HTW_WH_G, Polygon_Berlin_HTW_WH_G_EWKT.getWKT());
        Assert.assertEquals(srs_int, Polygon_Berlin_HTW_WH_G_EWKT.getSRS());
    }

    @Test
    public void createdSharkGeometryByGeographicalWKT_POLYGON_2() throws SharkKBException {
        Assert.assertEquals(string_Polygon_Berlin_HTW_WH_Complete, Polygon_Berlin_HTW_WH_Complete.getWKT());
    }

    @Test
    public void createdSharkGeometryByGeographicalEWKT_POLYGON_2() throws SharkKBException {
        Assert.assertEquals(string_Polygon_Berlin_HTW_WH_Complete, Polygon_Berlin_HTW_WH_Complete_EWKT.getWKT());
        Assert.assertEquals(srs_int, Polygon_Berlin_HTW_WH_Complete_EWKT.getSRS());
    }

    @Test
    public void createdSharkGeometryByGeographicalWKT_POLYGON_3() throws SharkKBException {
        Assert.assertEquals(string_Polygon_Berlin_HTW_WH_Complete_WITHOUT_G, Polygon_Berlin_HTW_WH_Complete_WITHOUT_G.getWKT());
    }

    @Test
    public void createdSharkGeometryByGeographicalEWKT_POLYGON_3() throws SharkKBException {
        Assert.assertEquals(string_Polygon_Berlin_HTW_WH_Complete_WITHOUT_G, Polygon_Berlin_HTW_WH_Complete_WITHOUT_G_EWKT.getWKT());
        Assert.assertEquals(srs_int, Polygon_Berlin_HTW_WH_Complete_WITHOUT_G_EWKT.getSRS());
    }

    @Test
    public void createdSharkGeometryByGeographicalWKT_MULTIPOINT() throws SharkKBException {
        Assert.assertEquals(string_Multipoint_Berlin_HTW_WH_G, Multipoint_Berlin_HTW_WH_G.getWKT());
    }

    @Test
    public void createdSharkGeometryByGeographicalEWKT_MULTIPOINT() throws SharkKBException {
        Assert.assertEquals(string_Multipoint_Berlin_HTW_WH_G, Multipoint_Berlin_HTW_WH_G_EWKT.getWKT());
        Assert.assertEquals(srs_int, Multipoint_Berlin_HTW_WH_G_EWKT.getSRS());
    }

    @Test
    public void createdSharkGeometryByGeographicalWKT_MULTILINESTRING() throws SharkKBException {
        Assert.assertEquals(string_Multilinestring_Berlin_HTW_WH_G, Multilinestring_Berlin_HTW_WH_G.getWKT());
    }

    @Test
    public void createdSharkGeometryByGeographicalEWKT_MULTILINESTRING() throws SharkKBException {
        Assert.assertEquals(string_Multilinestring_Berlin_HTW_WH_G, Multilinestring_Berlin_HTW_WH_G_EWKT.getWKT());
        Assert.assertEquals(srs_int, Multilinestring_Berlin_HTW_WH_G_EWKT.getSRS());
    }

    @Test
    public void createSharkGeometryByGeographicalWKT_MULTIPOLYGON() throws SharkKBException {
        Assert.assertEquals(string_Multipolygon_Berlin_HTW_Complete, Multipolygon_Berlin_HTW_Complete.getWKT());
    }

    @Test
    public void createSharkGeometryByGeographicalEWKT_MULTIPOLYGON() throws SharkKBException {
        Assert.assertEquals(string_Multipolygon_Berlin_HTW_Complete, Multipolygon_Berlin_HTW_Complete_EWKT.getWKT());
        Assert.assertEquals(srs_int, Multipolygon_Berlin_HTW_Complete_EWKT.getSRS());
    }

    @Test
    public void createSharkGeometryByGeographicalWKT_MULTIPOLYGON_2() throws SharkKBException {
        Assert.assertEquals(string_Multipolygon_Berlin_HTW_Complete_WITHOUT_WH_G, Multipolygon_Berlin_HTW_Complete_WITHOUT_WH_G.getWKT());
    }

    @Test
    public void createSharkGeometryByGeographicalWKT_GEOMETRYCOLLECTION() throws SharkKBException {
        Assert.assertEquals(string_Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON, Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON.getWKT());
    }

    @Test
    public void createSharkGeometryByGeographicalEWKT_GEOMETRYCOLLECTION() throws SharkKBException {
        Assert.assertEquals(string_Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON, Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON_EWKT.getWKT());
    }

    @Test
    public void createSharkGeometryByGeographicalEWKT_GEOMETRYCOLLECTION_3() throws SharkKBException {
        Assert.assertEquals(string_Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON_WITHOUT_WH_G, Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON_WITHOUT_WH_G.getWKT());
    }

    @Test
    public void createSharkGeometryByGeographicalEWKT_GEOMETRYCOLLECTION_4() throws SharkKBException {
        Assert.assertEquals(string_Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON_WITHOUT_WH_G, Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON_WITHOUT_WH_G.getWKT());
    }

    /*
     * identical tests start here ->
     */
    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_POINT() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_WH_G);
        Assert.assertEquals(true, usedFunctionClass.identical(tag1, tag1));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_POINT_False() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_WH_G_EWKT);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_TA);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_POINT_LINESTRING() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_G);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_POINT_POLYGON() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_POINT_MULTIPOINT() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_POINT_MULTILINESTRING() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_POINT_MULTIPOLYGON() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_POINT_GEOMETRYCOLLECTION() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_LINESTRING() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_G);
        Assert.assertEquals(true, usedFunctionClass.identical(tag1, tag1));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_LINESTRING_POINT() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_LINESTRING_LINESTRING() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_G_EWKT);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_Complete_EWKT);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_LINESTRING_POLYGON() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_G_EWKT);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_G);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_LINESTRING_MULTIPOINT() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_G_EWKT);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_LINESTRING_MULTILINESTRING_TRUE() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_G_EWKT);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_G);
        Assert.assertEquals(true, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_LINESTRING_MULTILINESTRING() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_LINESTRING_MULTIPOLYGON() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_LINESTRING_GEOMETRYCOLLECTION() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON_EWKT);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_POLYGON() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_G);
        Assert.assertEquals(true, usedFunctionClass.identical(tag1, tag1));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_POLYGON_POINT() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_Complete_EWKT);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_WH_G);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_POLYGON_LINESTRING() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_Complete_EWKT);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_POLYGON_POLYGON() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_Complete_EWKT);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_Complete_WITHOUT_G_EWKT);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_POLYGON_MULTIPOINT() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_Complete_EWKT);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_POLYGON_MULTILINESTRING() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_Complete_EWKT);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_POLYGON_MULTIPOLYGON() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_POLYGON_GEOMETRYCOLLECTION() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_MULTIPOINT() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_G);
        Assert.assertEquals(true, usedFunctionClass.identical(tag1, tag1));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_MULTIPOINT_POINT() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_WH_G);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_MULTIPOINT_LINESTRING() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_G);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_MULTIPOINT_POLYGON() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_G_EWKT);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_MULTIPOINT_MULTIPOINT() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_G_EWKT);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_MULTIPOINT_MULTIPOINT_TRUE() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_G_EWKT);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_G);
        Assert.assertEquals(true, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_MULTIPOINT_MULTILINESTRING() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_G_EWKT);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_G_EWKT);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_MULTIPOINT_MULTIPOLYGON() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete_EWKT);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_MULTIPOINT_GEOMETRYCOLLECTION() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_MULTILINESTRING() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_Complete_EWKT);
        Assert.assertEquals(true, usedFunctionClass.identical(tag1, tag1));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_MULTILINESTRING_POINT() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_WH_G);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_MULTILINESTRING_LINESTRING() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_MULTILINESTRING_POLYGON() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_MULTILINESTRING_MULTIPOINT() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_MULTILINESTRING_MULTILINESTRING() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_MULTILINESTRING_MULTIPOLYGON() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_MULTILINESTRING_GEOMETRYCOLLECTION() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_MULTIPOLYGON() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete);
        Assert.assertEquals(true, usedFunctionClass.identical(tag1, tag1));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_MULTIPOLYGON_POINT() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_WH_G);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_MULTIPOLYGON_LINESTRING() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete_EWKT);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_Complete_EWKT);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_MULTIPOLYGON_POLYGON() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete_EWKT);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_MULTIPOLYGON_MULTIPOINT() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete_EWKT);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_MULTIPOLYGON_MULTILINESTRING() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete_EWKT);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_MULTIPOLYGON_MULTIPOLYGON() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete_EWKT);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete_WITHOUT_WH_G);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_MULTIPOLYGON_GEOMETRYCOLLECTION() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete_EWKT);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON);
        Assert.assertEquals(true, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_GEOMETRYCOLLECTION() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON);
        Assert.assertEquals(true, usedFunctionClass.identical(tag1, tag1));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_GEOMETRYCOLLECTION_POINT() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_WH_Complete_With_MULTIPOLYGON);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_WH_G);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_GEOMETRYCOLLECTION_LINESTRINGN() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_WH_Complete_With_MULTIPOLYGON);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_GEOMETRYCOLLECTION_POLYGON() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_WH_Complete_With_MULTIPOLYGON);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_GEOMETRYCOLLECTION_MULTIPOINT() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_WH_Complete_With_MULTIPOLYGON);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_GEOMETRYCOLLECTION_MULTILINESTRING() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_WH_Complete_With_MULTIPOLYGON);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_GEOMETRYCOLLECTION_MULTIPOLYGON() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete);
        Assert.assertEquals(true, usedFunctionClass.identical(tag1, tag2));
    }

    @Test
    public void checkSpatialAlgebraIdenticalSemanticTags_GEOMETRYCOLLECTION_GEOMETRYCOLLECTION() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON_EWKT);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON_WITHOUT_WH_G);
        Assert.assertEquals(false, usedFunctionClass.identical(tag1, tag2));
    }

    /*
     * isIn tests start here ->
     */
    @Test
    public void checkSpatialAlgebraIsInSemanticTags_POINT() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_WH_G);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_POINT_POINT_FALSE() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_TA);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_POINT_LINESTRING_FALSE() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_G);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(true, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_POINT_POLYGON_FALSE() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(true, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_POINT_MULTIPOINT_FALSE() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(true, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_POINT_MULTILINESTRING_FALSE() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_G);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(true, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_POINT_MULTIPOLYGON_FALSE() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(true, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_POINT_GEOMETRYCOLLECTION_FALSE() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(true, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_LINESTRING() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_Complete);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_LINESTRING_POINT() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_WH_G);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_LINESTRING_LINESTRING() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_G);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_LINESTRING_LINESTRING_FALSE() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(true, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_LINESTRING_POLYGON_FALSE() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(true, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_LINESTRING_MULTIPOINT() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_G);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_LINESTRING_MULTIPOINT_FALSE() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_LINESTRING_MULTILINESTRING() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_G);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(true, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_LINESTRING_MULTILINESTRING_FALSE() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(true, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_LINESTRING_MULTIPOLYGON() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_G);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_LINESTRING_MULTIPOLYGON_FALSE() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_LINESTRING_GEOMETRYCOLLECTION() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(true, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_LINESTRING_GEOMETRYCOLLECTION_FALSE() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(true, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_POLYGON() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_Complete_EWKT);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_POLYGON_POINT() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_WH_G);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_POLYGON_POINT_FALSE() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_TA);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_POLYGON_LINESTRING() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_G);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_POLYGON_LINESTRING_FALSE() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_POLYGON_POLYGON() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_G);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_POLYGON_POLYGON_FALSE() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_TA_Complete);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_POLYGON_MULTIPOINT() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_Complete);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_POLYGON_MULTIPOINT_FALSE() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_POLYGON_MULTILINESTRING() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_Complete);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_POLYGON_MULTILINESTRING_FALSE() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_POLYGON_MULTIPOLYGON_FALSE() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete_EWKT);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(true, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_POLYGON_GEOMETRYCOLLECTION() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(true, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_MULTIPOINT() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_G_EWKT);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_MULTIPOINT_POINT() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_WH_G);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_MULTIPOINT_LINESTRING() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(true, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_MULTIPOINT_POLYGON() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_G);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(true, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_MULTIPOINT_MULTIPOINT() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_G_EWKT);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_MULTIPOINT_MULTIPOINT_FALSE() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_G_EWKT);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(true, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_MULTIPOINT_MULTILINESTRING() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_G_EWKT);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_G);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(true, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_MULTIPOINT_MULTIPOLYGON() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(true, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_MULTIPOINT_GEOMETRYCOLLECTION() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(true, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_MULTILINESTRING() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_Complete);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_MULTILINESTRING_POINT() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_WH_G);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_MULTILINESTRING_LINESTRING() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_WH_G);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_MULTILINESTRING_POLYGON() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(true, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_MULTILINESTRING_MULTIPOINT() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_Complete);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_MULTILINESTRING_MULTILINESTRING() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_G);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_MULTILINESTRING_MULTILINESTRING_FALSE() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_Complete);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(true, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_MULTILINESTRING_MULTIPOLYGON() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(true, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_MULTILINESTRING_GEOMETRYCOLLECTION() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(true, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_MULTIPOLYGON() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete_WITHOUT_WH_G);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_MULTIPOLYGON_POINT() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_WH_G);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_MULTIPOLYGON_LINESTRING() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_Complete);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_MULTIPOLYGON_POLYGON() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_TA_Complete);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_MULTIPOLYGON_MULTIPOINT() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_Complete);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_MULTIPOLYGON_MULTILINESTRING() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_Complete);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_MULTIPOLYGON_MULTIPOLYGON() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_WH_Complete);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_MULTIPOLYGON_MULTIPOLYGON_FALSE() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(true, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_MULTIPOLYGON_GEOMETRYCOLLECTION() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(true, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_GEOMETRYCOLLECTION() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON_WITHOUT_WH_G);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_GEOMETRYCOLLECTION_POINT() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON_WITHOUT_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_TA);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_GEOMETRYCOLLECTION_POINT_2() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON_WITHOUT_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Point_Berlin_HTW_WH_G);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_GEOMETRYCOLLECTION_LINESTRING() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_WH_Complete_With_MULTIPOLYGON);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_G);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_GEOMETRYCOLLECTION_LINESTRING_FALSE() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON_WITHOUT_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(LineString_Berlin_HTW_WH_G);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_GEOMETRYCOLLECTION_POLYGON() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_WH_Complete_With_MULTIPOLYGON);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_G);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_GEOMETRYCOLLECTION_POLYGON_FALSE() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON_WITHOUT_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Polygon_Berlin_HTW_WH_G);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_GEOMETRYCOLLECTION_MULTIPOINT() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON_EWKT);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_Complete);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_GEOMETRYCOLLECTION_MULTIPOINT_2() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON_WITHOUT_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipoint_Berlin_HTW_WH_G);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_GEOMETRYCOLLECTION_MULTILINESTRING() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON_EWKT);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_Complete);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_GEOMETRYCOLLECTION_MULTILINESTRING_2() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON_WITHOUT_WH_G);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multilinestring_Berlin_HTW_WH_G);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(false, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_GEOMETRYCOLLECTION_MULTIPOLYGON() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_Complete_With_MULTIPOLYGON_EWKT);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete_EWKT);
        Assert.assertEquals(true, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(true, usedFunctionClass.isIn(tag2, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsInSemanticTags_GEOMETRYCOLLECTION_MULTIPOLYGON_FALSE() throws SharkKBException {
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(Geometrycollection_Berlin_HTW_WH_Complete_With_MULTIPOLYGON);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete);
        Assert.assertEquals(false, usedFunctionClass.isIn(tag1, tag2));
        Assert.assertEquals(true, usedFunctionClass.isIn(tag2, tag1));
    }

    //Todo add Fragment Tests
    @Test
    public void checkSpatialAlgebraIsFragment() throws SharkKBException {
        String string_Blue_Collection = "GEOMETRYCOLLECTION (POLYGON ((166 426, 480 426, 480 230, 166 230, 166 426)), "
                + "LINESTRING (693 485, 360 480, 910 310, 570 140), "
                + "POINT (530 340), "
                + "POINT (434 196), "
                + "POINT (771 415), "
                + "POINT (705 328), "
                + "LINESTRING (180 520, 46 506, 115 376, 50 330, 80 220), "
                + "POLYGON ((210 230, 278 230, 278 115, 210 115, 210 230)))";
        String string_Red_Collection = "GEOMETRYCOLLECTION (POLYGON ((270 520, 534 520, 534 386, 270 386, 270 520)), "
                + "POLYGON ((346 294, 670 294, 670 90, 346 90, 346 294)), "
                + "LINESTRING (620 520, 790 170), "
                + "POLYGON ((250 200, 346 200, 346 90, 250 90, 250 200)))";
        String string_ExpectedResult_Collection = "GEOMETRYCOLLECTION (POLYGON ((480 386, 270 386, 270 426, 480 426, 480 386)), "
                + "MULTIPOLYGON (((346 294, 480 294, 480 230, 346 230, 346 294)), "
                + "((250 115, 250 200, 278 200, 278 115, 250 115))), "
                + "LINESTRING (534 482.6126126126126, 360 480, 534 426.2181818181818), "
                + "LINESTRING (670 190, 570 140), "
                + "MULTIPOINT ((637.4054514480409 484.16524701873936), (688.7897310513447 378.37408312958433), (758.735632183908 234.367816091954)), "
                + "POINT (434 196))";
        SharkGeometry Blue_Collection = InMemoSharkGeometry.createGeomByWKT(string_Blue_Collection);
        SharkGeometry Red_Collection = InMemoSharkGeometry.createGeomByWKT(string_Red_Collection);
        SharkGeometry ExpectedResult_Collection = InMemoSharkGeometry.createGeomByWKT(string_ExpectedResult_Collection);
        SpatialSemanticTag sourceSpatialTag = InMemoSharkKB.createInMemoSpatialSemanticTag(Blue_Collection);
        SpatialSemanticTag anchorSpatialTag = InMemoSharkKB.createInMemoSpatialSemanticTag(Red_Collection);
        SpatialSemanticTag expectedResultSpatialTag = InMemoSharkKB.createInMemoSpatialSemanticTag(ExpectedResult_Collection);
        SpatialSTSet sourceSTSet = InMemoSharkKB.createInMemoSpatialSTSet();
        sourceSTSet.merge(sourceSpatialTag);
        SpatialSTSet fragmentSTSet = InMemoSharkKB.createInMemoSpatialSTSet();
        SpatialSemanticTag containTag = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete);
        fragmentSTSet.merge(containTag);
        SpatialSTSet resultFragmentSTSet = usedFunctionClass.fragment(fragmentSTSet, sourceSTSet, anchorSpatialTag);

        Enumeration<SpatialSemanticTag> spatialEnum = resultFragmentSTSet.spatialTags();
        SpatialSemanticTag firstTag = spatialEnum.nextElement();
        SpatialSemanticTag secondTag = spatialEnum.nextElement();
        Assert.assertEquals(firstTag, containTag);
        Assert.assertEquals(true, usedFunctionClass.identical(secondTag, expectedResultSpatialTag));
        Assert.assertEquals(secondTag.getGeometry().getWKT(), expectedResultSpatialTag.getGeometry().getWKT());
        Assert.assertEquals(true, usedFunctionClass.identical(sourceSpatialTag, sourceSpatialTag));
        Assert.assertEquals(true, usedFunctionClass.identical(anchorSpatialTag, anchorSpatialTag));
    }

    @Test
    public void checkSpatialAlgebraIsFragment_HTW_WH_1() throws SharkKBException {
        SharkGeometry htw_SharkGeom1 = InMemoSharkGeometry.createGeomByWKT("GEOMETRYCOLLECTION (POLYGON ((52.45606650054853 13.523988202214241, "
                + "52.45549525426796 13.525406420230865, 52.45515160397337 13.525016158819199, 52.45582827785886 13.523717299103737, 52.45606650054853 13.523988202214241)), "
                + "POLYGON ((52.456114 13.52582216, 52.45680046 13.52393389, 52.4583891 13.52590799, 52.45768305 13.5277319, 52.456114 13.52582216)))");

        SharkGeometry fragment_Testcase = InMemoSharkGeometry.createGeomByWKT("POLYGON ((52.456442857142854 13.526571428571428, "
                + "52.456442857142854 13.523185714285715, 52.45574285714286 13.523185714285715, 52.45574285714286 13.526571428571428, "
                + "52.456442857142854 13.526571428571428))");

        SharkGeometry ExpectedResult_Collection = InMemoSharkGeometry.createGeomByWKT("GEOMETRYCOLLECTION (POLYGON ((52.456442857142854 13.52491756093066, 52.456114 13.52582216, "
                + "52.456442857142854 13.526222422349827, 52.456442857142854 13.52491756093066)), POLYGON ((52.45574285714286 13.524791703128813, "
                + "52.45606650054853 13.523988202214241, 52.45582827785886 13.523717299103737, 52.45574285714286 13.5238812621726, "
                + "52.45574285714286 13.524791703128813)))");

        SpatialSemanticTag htw_tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(htw_SharkGeom1);

        SpatialSemanticTag anchorSpatialTag = InMemoSharkKB.createInMemoSpatialSemanticTag(fragment_Testcase);
        SpatialSemanticTag expectedResultSpatialTag = InMemoSharkKB.createInMemoSpatialSemanticTag(ExpectedResult_Collection);
        SpatialSTSet sourceSTSet = InMemoSharkKB.createInMemoSpatialSTSet();
        sourceSTSet.merge(htw_tag1);
        SpatialSTSet fragmentSTSet = InMemoSharkKB.createInMemoSpatialSTSet();
        SpatialSemanticTag containTag = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete);
        fragmentSTSet.merge(containTag);
        SpatialSTSet resultFragmentSTSet = usedFunctionClass.fragment(fragmentSTSet, sourceSTSet, anchorSpatialTag);

        Enumeration<SpatialSemanticTag> spatialEnum = resultFragmentSTSet.spatialTags();
        SpatialSemanticTag firstTag = spatialEnum.nextElement();
        SpatialSemanticTag secondTag = spatialEnum.nextElement();
        Assert.assertEquals(firstTag, containTag);
        Assert.assertEquals(true, usedFunctionClass.identical(secondTag, expectedResultSpatialTag));
        Assert.assertEquals(true, usedFunctionClass.identical(htw_tag1, htw_tag1));
        Assert.assertEquals(true, usedFunctionClass.identical(anchorSpatialTag, anchorSpatialTag));
    }

    @Test
    public void checkSpatialAlgebraIsFragment_HTW_WH_2() throws SharkKBException {
        SharkGeometry htw_SharkGeom1 = InMemoSharkGeometry.createGeomByWKT(string_Polygon_Berlin_HTW_WH_G);
        SharkGeometry htw_SharkGeom2 = InMemoSharkGeometry.createGeomByWKT("Polygon ((52.456114 13.52582216, 52.45680046 13.52393389,"
                + "52.4583891 13.52590799, 52.45768305 13.5277319, 52.456114 13.52582216))");

        SharkGeometry fragment_Testcase = InMemoSharkGeometry.createGeomByWKT("POLYGON ((52.456442857142854 13.526571428571428, "
                + "52.456442857142854 13.523185714285715, 52.45574285714286 13.523185714285715, 52.45574285714286 13.526571428571428, "
                + "52.456442857142854 13.526571428571428))");

        SharkGeometry ExpectedResult_Collection = InMemoSharkGeometry.createGeomByWKT("GEOMETRYCOLLECTION (POLYGON ((52.456442857142854 13.52491756093066, 52.456114 13.52582216, "
                + "52.456442857142854 13.526222422349827, 52.456442857142854 13.52491756093066)), POLYGON ((52.45574285714286 13.524791703128813, "
                + "52.45606650054853 13.523988202214241, 52.45582827785886 13.523717299103737, 52.45574285714286 13.5238812621726, "
                + "52.45574285714286 13.524791703128813)))");

        SpatialSemanticTag htw_tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(htw_SharkGeom1);
        SpatialSemanticTag htw_tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(htw_SharkGeom2);

        SpatialSemanticTag anchorSpatialTag = InMemoSharkKB.createInMemoSpatialSemanticTag(fragment_Testcase);
        SpatialSemanticTag expectedResultSpatialTag = InMemoSharkKB.createInMemoSpatialSemanticTag(ExpectedResult_Collection);
        SpatialSTSet sourceSTSet = InMemoSharkKB.createInMemoSpatialSTSet();
        sourceSTSet.merge(htw_tag1).merge(htw_tag2);
        SpatialSTSet fragmentSTSet = InMemoSharkKB.createInMemoSpatialSTSet();
        SpatialSemanticTag containTag = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete);
        fragmentSTSet.merge(containTag);
        SpatialSTSet resultFragmentSTSet = usedFunctionClass.fragment(fragmentSTSet, sourceSTSet, anchorSpatialTag);

        Enumeration<SpatialSemanticTag> spatialEnum = resultFragmentSTSet.spatialTags();
        SpatialSemanticTag firstTag = spatialEnum.nextElement();
        SpatialSemanticTag secondTag = spatialEnum.nextElement();
        Assert.assertEquals(firstTag, containTag);
        Assert.assertEquals(true, usedFunctionClass.identical(secondTag, expectedResultSpatialTag));
        Assert.assertEquals(true, usedFunctionClass.identical(htw_tag1, htw_tag1));
        Assert.assertEquals(true, usedFunctionClass.identical(htw_tag2, htw_tag2));
        Assert.assertEquals(true, usedFunctionClass.identical(anchorSpatialTag, anchorSpatialTag));
    }

    @Test
    public void checkSpatialAlgebraIsFragment_HTW_WH_3() throws SharkKBException {
        SharkGeometry htw_SharkGeom1 = InMemoSharkGeometry.createGeomByWKT(string_Polygon_Berlin_HTW_WH_G);
        SharkGeometry htw_SharkGeom2 = InMemoSharkGeometry.createGeomByWKT("Polygon ((52.456114 13.52582216, 52.45680046 13.52393389,"
                + "52.4583891 13.52590799, 52.45768305 13.5277319, 52.456114 13.52582216))");

        SharkGeometry fragment_Testcase = InMemoSharkGeometry.createGeomByWKT("GEOMETRYCOLLECTION (POLYGON ((52.456442857142854 13.526571428571428, "
                + "52.456442857142854 13.523185714285715, 52.45574285714286 13.523185714285715, 52.45574285714286 13.526571428571428, "
                + "52.456442857142854 13.526571428571428)), POLYGON ((52.45922857142857 13.524657142857142, 52.45727142857143 13.524657142857142, "
                + "52.45727142857143 13.528042857142857, 52.45922857142857 13.528042857142857, 52.45922857142857 13.524657142857142)))");

        SharkGeometry ExpectedResult_Collection = InMemoSharkGeometry.createGeomByWKT("GEOMETRYCOLLECTION (POLYGON ((52.456442857142854 13.52491756093066, 52.456114 13.52582216, "
                + "52.456442857142854 13.526222422349827, 52.456442857142854 13.52491756093066)), POLYGON ((52.45574285714286 13.524791703128813, "
                + "52.45606650054853 13.523988202214241, 52.45582827785886 13.523717299103737, 52.45574285714286 13.5238812621726, "
                + "52.45574285714286 13.524791703128813)), POLYGON ((52.45727142857143 13.527230902640454, 52.45768305 13.5277319, 52.4583891 13.52590799, "
                + "52.457382491517635 13.524657142857142, 52.45727142857143 13.524657142857142, 52.45727142857143 13.527230902640454)))");

        SpatialSemanticTag htw_tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(htw_SharkGeom1);
        SpatialSemanticTag htw_tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(htw_SharkGeom2);

        SpatialSemanticTag anchorSpatialTag = InMemoSharkKB.createInMemoSpatialSemanticTag(fragment_Testcase);
        SpatialSemanticTag expectedResultSpatialTag = InMemoSharkKB.createInMemoSpatialSemanticTag(ExpectedResult_Collection);
        SpatialSTSet sourceSTSet = InMemoSharkKB.createInMemoSpatialSTSet();
        sourceSTSet.merge(htw_tag1).merge(htw_tag2);
        SpatialSTSet fragmentSTSet = InMemoSharkKB.createInMemoSpatialSTSet();
        SpatialSemanticTag containTag = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete);
        fragmentSTSet.merge(containTag);
        SpatialSTSet resultFragmentSTSet = usedFunctionClass.fragment(fragmentSTSet, sourceSTSet, anchorSpatialTag);

        Enumeration<SpatialSemanticTag> spatialEnum = resultFragmentSTSet.spatialTags();
        SpatialSemanticTag firstTag = spatialEnum.nextElement();
        SpatialSemanticTag secondTag = spatialEnum.nextElement();
        Assert.assertEquals(firstTag, containTag);
        Assert.assertEquals(true, usedFunctionClass.identical(secondTag, expectedResultSpatialTag));
    }

    @Test
    public void checkSpatialAlgebraIsFragment_HTW_WH_4() throws SharkKBException {
        SharkGeometry htw_SharkGeom1 = InMemoSharkGeometry.createGeomByWKT(string_Polygon_Berlin_HTW_WH_G);
        SharkGeometry htw_SharkGeom2 = InMemoSharkGeometry.createGeomByWKT("Polygon ((52.456114 13.52582216, 52.45680046 13.52393389,"
                + "52.4583891 13.52590799, 52.45768305 13.5277319, 52.456114 13.52582216))");
        SharkGeometry htw_SharkGeom3 = InMemoSharkGeometry.createGeomByWKT("MULTIPOINT (52.45683315 13.52764606, 52.45662394 13.52822542, 52.45596363 13.52752805, 52.45621207 13.52636933)");

        SharkGeometry fragment_Testcase = InMemoSharkGeometry.createGeomByWKT("GEOMETRYCOLLECTION (POLYGON ((52.456442857142854 13.526571428571428, "
                + "52.456442857142854 13.523185714285715, 52.45574285714286 13.523185714285715, 52.45574285714286 13.526571428571428, "
                + "52.456442857142854 13.526571428571428)), POLYGON ((52.45922857142857 13.524657142857142, 52.45727142857143 13.524657142857142, "
                + "52.45727142857143 13.528042857142857, 52.45922857142857 13.528042857142857, 52.45922857142857 13.524657142857142)), POINT(52.45662394 13.52822542))");

        SharkGeometry ExpectedResult_Collection = InMemoSharkGeometry.createGeomByWKT("GEOMETRYCOLLECTION (POLYGON ((52.456442857142854 13.52491756093066, 52.456114 13.52582216, "
                + "52.456442857142854 13.526222422349827, 52.456442857142854 13.52491756093066)), POLYGON ((52.45574285714286 13.524791703128813, "
                + "52.45606650054853 13.523988202214241, 52.45582827785886 13.523717299103737, 52.45574285714286 13.5238812621726, "
                + "52.45574285714286 13.524791703128813)), POLYGON ((52.45727142857143 13.527230902640454, 52.45768305 13.5277319, 52.4583891 13.52590799, "
                + "52.457382491517635 13.524657142857142, 52.45727142857143 13.524657142857142, 52.45727142857143 13.527230902640454)), POINT (52.45662394 13.52822542))");

        SpatialSemanticTag htw_tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(htw_SharkGeom1);
        SpatialSemanticTag htw_tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(htw_SharkGeom2);
        SpatialSemanticTag htw_tag3 = InMemoSharkKB.createInMemoSpatialSemanticTag(htw_SharkGeom3);

        SpatialSemanticTag anchorSpatialTag = InMemoSharkKB.createInMemoSpatialSemanticTag(fragment_Testcase);
        SpatialSemanticTag expectedResultSpatialTag = InMemoSharkKB.createInMemoSpatialSemanticTag(ExpectedResult_Collection);
        SpatialSTSet sourceSTSet = InMemoSharkKB.createInMemoSpatialSTSet();
        sourceSTSet.merge(htw_tag1).merge(htw_tag2);
        sourceSTSet.merge(htw_tag3);
        SpatialSTSet fragmentSTSet = InMemoSharkKB.createInMemoSpatialSTSet();
        SpatialSemanticTag containTag = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete);
        fragmentSTSet.merge(containTag);
        SpatialSTSet resultFragmentSTSet = usedFunctionClass.fragment(fragmentSTSet, sourceSTSet, anchorSpatialTag);

        Enumeration<SpatialSemanticTag> spatialEnum = resultFragmentSTSet.spatialTags();
        SpatialSemanticTag firstTag = spatialEnum.nextElement();
        SpatialSemanticTag secondTag = spatialEnum.nextElement();
        Assert.assertEquals(firstTag, containTag);
        Assert.assertEquals(true, usedFunctionClass.identical(secondTag, expectedResultSpatialTag));
    }

    @Test
    public void checkSpatialAlgebraIsFragment_HTW_WH_5() throws SharkKBException {
        SharkGeometry htw_SharkGeom1 = InMemoSharkGeometry.createGeomByWKT(string_Polygon_Berlin_HTW_WH_G);
        SharkGeometry htw_SharkGeom2 = InMemoSharkGeometry.createGeomByWKT("Polygon ((52.456114 13.52582216, 52.45680046 13.52393389,"
                + "52.4583891 13.52590799, 52.45768305 13.5277319, 52.456114 13.52582216))");
        SharkGeometry htw_SharkGeom3 = InMemoSharkGeometry.createGeomByWKT("MULTIPOINT (52.45683315 13.52764606, 52.45662394 13.52822542, 52.45596363 13.52752805, 52.45621207 13.52636933)");
        SharkGeometry htw_SharkGeom4 = InMemoSharkGeometry.createGeomByWKT("MULTILINESTRING ((52.45683315 13.52764606, 52.45662394 13.52822542, 52.45596363 13.52752805, 52.45621207 13.52636933))");

        SharkGeometry fragment_Testcase = InMemoSharkGeometry.createGeomByWKT("GEOMETRYCOLLECTION (POLYGON ((52.456442857142854 13.526571428571428, "
                + "52.456442857142854 13.523185714285715, 52.45574285714286 13.523185714285715, 52.45574285714286 13.526571428571428, "
                + "52.456442857142854 13.526571428571428)), POLYGON ((52.45922857142857 13.524657142857142, 52.45727142857143 13.524657142857142, "
                + "52.45727142857143 13.528042857142857, 52.45922857142857 13.528042857142857, 52.45922857142857 13.524657142857142)), POINT(52.45662394 13.52822542),"
                + "MULTILINESTRING ((52.45596363 13.52752805, 52.45621207 13.52636933)))");

        SharkGeometry ExpectedResult_Collection = InMemoSharkGeometry.createGeomByWKT("GEOMETRYCOLLECTION (POLYGON ((52.456442857142854 13.52491756093066, 52.456114 13.52582216, "
                + "52.456442857142854 13.526222422349827, 52.456442857142854 13.52491756093066)), POLYGON ((52.45574285714286 13.524791703128813, "
                + "52.45606650054853 13.523988202214241, 52.45582827785886 13.523717299103737, 52.45574285714286 13.5238812621726, "
                + "52.45574285714286 13.524791703128813)), POLYGON ((52.45727142857143 13.527230902640454, 52.45768305 13.5277319, 52.4583891 13.52590799, "
                + "52.457382491517635 13.524657142857142, 52.45727142857143 13.524657142857142, 52.45727142857143 13.527230902640454)), POINT (52.45662394 13.52822542),"
                + "MULTILINESTRING ((52.45616873824678 13.526571428571428, 52.45621207 13.52636933), " 
                + "(52.45596363 13.52752805, 52.45621207 13.52636933)))");

        SpatialSemanticTag htw_tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(htw_SharkGeom1);
        SpatialSemanticTag htw_tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(htw_SharkGeom2);
        SpatialSemanticTag htw_tag3 = InMemoSharkKB.createInMemoSpatialSemanticTag(htw_SharkGeom3);
        SpatialSemanticTag htw_tag4 = InMemoSharkKB.createInMemoSpatialSemanticTag(htw_SharkGeom4);

        SpatialSemanticTag anchorSpatialTag = InMemoSharkKB.createInMemoSpatialSemanticTag(fragment_Testcase);
        SpatialSemanticTag expectedResultSpatialTag = InMemoSharkKB.createInMemoSpatialSemanticTag(ExpectedResult_Collection);
        SpatialSTSet sourceSTSet = InMemoSharkKB.createInMemoSpatialSTSet();
        sourceSTSet.merge(htw_tag1);
        sourceSTSet.merge(htw_tag2);
        sourceSTSet.merge(htw_tag3);
        sourceSTSet.merge(htw_tag4);
        SpatialSTSet fragmentSTSet = InMemoSharkKB.createInMemoSpatialSTSet();
        SpatialSemanticTag containTag = InMemoSharkKB.createInMemoSpatialSemanticTag(Multipolygon_Berlin_HTW_Complete);
        fragmentSTSet.merge(containTag);
        SpatialSTSet resultFragmentSTSet = usedFunctionClass.fragment(fragmentSTSet, sourceSTSet, anchorSpatialTag);

        Enumeration<SpatialSemanticTag> spatialEnum = resultFragmentSTSet.spatialTags();
        SpatialSemanticTag firstTag = spatialEnum.nextElement();
        SpatialSemanticTag secondTag = spatialEnum.nextElement();
        System.out.println(secondTag.getGeometry().getWKT());
        Assert.assertEquals(firstTag, containTag);
        Assert.assertEquals(true, usedFunctionClass.identical(secondTag, expectedResultSpatialTag));
    }

    //Todo add FrameworkTest
    @Test
    public void checkSpatialAlgebraIdentical_SharkSpatialAlgebra() throws SharkKBException {
        SharkGeometry shark_geom1 = net.sharkfw.knowledgeBase.geom.inmemory.InMemoSharkGeometry.createGeomByWKT(string_Multipolygon_Berlin_HTW_WH_Complete);
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(shark_geom1);
        net.sharkfw.knowledgeBase.geom.SpatialAlgebra a = new net.sharkfw.knowledgeBase.geom.SpatialAlgebra();
        Assert.assertEquals(true, a.identical(tag1, tag1));
    }

    @Test
    public void checkSpatialAlgebraIsIn_SharkSpatialAlgebra() throws SharkKBException {
        SharkGeometry shark_geom1 = net.sharkfw.knowledgeBase.geom.inmemory.InMemoSharkGeometry.createGeomByWKT(string_Multipolygon_Berlin_HTW_WH_Complete);
        SharkGeometry shark_geom2 = net.sharkfw.knowledgeBase.geom.inmemory.InMemoSharkGeometry.createGeomByWKT(string_Multipolygon_Berlin_HTW_Complete);
        SpatialSemanticTag tag1 = InMemoSharkKB.createInMemoSpatialSemanticTag(shark_geom1);
        SpatialSemanticTag tag2 = InMemoSharkKB.createInMemoSpatialSemanticTag(shark_geom2);
        net.sharkfw.knowledgeBase.geom.SpatialAlgebra a = new net.sharkfw.knowledgeBase.geom.SpatialAlgebra();
        Assert.assertEquals(true, a.isIn(tag1, tag2));
    }

}
