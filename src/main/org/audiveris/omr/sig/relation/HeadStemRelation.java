//------------------------------------------------------------------------------------------------//
//                                                                                                //
//                                  H e a d S t e m R e l a t i o n                               //
//                                                                                                //
//------------------------------------------------------------------------------------------------//
// <editor-fold defaultstate="collapsed" desc="hdr">
//
//  Copyright © Hervé Bitteur and others 2000-2017. All rights reserved.
//
//  This program is free software: you can redistribute it and/or modify it under the terms of the
//  GNU Affero General Public License as published by the Free Software Foundation, either version
//  3 of the License, or (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
//  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
//  See the GNU Affero General Public License for more details.
//
//  You should have received a copy of the GNU Affero General Public License along with this
//  program.  If not, see <http://www.gnu.org/licenses/>.
//------------------------------------------------------------------------------------------------//
// </editor-fold>
package org.audiveris.omr.sig.relation;

import org.audiveris.omr.constant.Constant;
import org.audiveris.omr.constant.ConstantSet;
import org.audiveris.omr.sheet.Scale;
import org.audiveris.omr.sig.inter.Inter;
import static org.audiveris.omr.sig.relation.StemPortion.*;
import org.audiveris.omr.util.HorizontalSide;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.geom.Line2D;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class {@code HeadStemRelation} represents the relation support between a head and a
 * stem.
 *
 * @author Hervé Bitteur
 */
@XmlRootElement(name = "head-stem")
public class HeadStemRelation
        extends AbstractStemConnection
{
    //~ Static fields/initializers -----------------------------------------------------------------

    private static final Constants constants = new Constants();

    private static final Logger logger = LoggerFactory.getLogger(
            HeadStemRelation.class);

    //~ Instance fields ----------------------------------------------------------------------------
    /** Which side of head is used?. */
    @XmlAttribute(name = "head-side")
    private HorizontalSide headSide;

    //~ Constructors -------------------------------------------------------------------------------
    /**
     * Creates a new {@code HeadStemRelation} object.
     */
    public HeadStemRelation ()
    {
    }

    //~ Methods ------------------------------------------------------------------------------------
    //------------------//
    // getXInGapMaximum //
    //------------------//
    public static Scale.Fraction getXInGapMaximum ()
    {
        return constants.xInGapMax;
    }

    //-------------------//
    // getXOutGapMaximum //
    //-------------------//
    public static Scale.Fraction getXOutGapMaximum ()
    {
        return constants.xOutGapMax;
    }

    //----------------//
    // getYGapMaximum //
    //----------------//
    public static Scale.Fraction getYGapMaximum ()
    {
        return constants.yGapMax;
    }

    /**
     * @return the headSide
     */
    public HorizontalSide getHeadSide ()
    {
        return headSide;
    }

    //----------------//
    // getStemPortion //
    //----------------//
    @Override
    public StemPortion getStemPortion (Inter source,
                                       Line2D stemLine,
                                       Scale scale)
    {
        final double margin = source.getBounds().height * constants.anchorHeightRatio.getValue();
        final double midStem = (stemLine.getY1() + stemLine.getY2()) / 2;
        final double anchor = extensionPoint.getY();

        if (anchor >= midStem) {
            return (anchor > (stemLine.getY2() - margin)) ? STEM_BOTTOM : STEM_MIDDLE;
        } else {
            return (anchor < (stemLine.getY1() + margin)) ? STEM_TOP : STEM_MIDDLE;
        }
    }

    /**
     * @param headSide the headSide to set
     */
    public void setHeadSide (HorizontalSide headSide)
    {
        this.headSide = headSide;
    }

    //----------------//
    // getSourceCoeff //
    //----------------//
    @Override
    protected double getSourceCoeff ()
    {
        return constants.headSupportCoeff.getValue();
    }

    //----------------//
    // getTargetCoeff //
    //----------------//
    @Override
    protected double getTargetCoeff ()
    {
        return constants.stemSupportCoeff.getValue();
    }

    //--------------//
    // getXInGapMax //
    //--------------//
    @Override
    protected Scale.Fraction getXInGapMax ()
    {
        return getXInGapMaximum();
    }

    //---------------//
    // getXOutGapMax //
    //---------------//
    @Override
    protected Scale.Fraction getXOutGapMax ()
    {
        return getXOutGapMaximum();
    }

    //------------//
    // getYGapMax //
    //------------//
    @Override
    protected Scale.Fraction getYGapMax ()
    {
        return getYGapMaximum();
    }

    @Override
    protected String internals ()
    {
        StringBuilder sb = new StringBuilder(super.internals());
        sb.append(" ").append(headSide);

        return sb.toString();
    }

    //~ Inner Classes ------------------------------------------------------------------------------
    //-----------//
    // Constants //
    //-----------//
    private static final class Constants
            extends ConstantSet
    {
        //~ Instance fields ------------------------------------------------------------------------

        private final Constant.Ratio headSupportCoeff = new Constant.Ratio(
                2,
                "Value for (source) head coeff in support formula");

        private final Constant.Ratio stemSupportCoeff = new Constant.Ratio(
                2,
                "Value for (target) stem coeff in support formula");

        private final Scale.Fraction yGapMax = new Scale.Fraction(
                0.8,
                "Maximum vertical gap between stem & head");

        private final Scale.Fraction xInGapMax = new Scale.Fraction(
                0.3,
                "Maximum horizontal overlap between stem & head");

        private final Scale.Fraction xOutGapMax = new Scale.Fraction(
                0.2,
                "Maximum horizontal gap between stem & head");

        private final Constant.Ratio anchorHeightRatio = new Constant.Ratio(
                0.25,
                "Vertical margin for stem anchor portion (as ratio of head height)");
    }
}
