//----------------------------------------------------------------------------//
//                                                                            //
//                       C h e c k i n g V i s i t o r                        //
//                                                                            //
//  Copyright (C) Herve Bitteur 2000-2006. All rights reserved.               //
//  This software is released under the terms of the GNU General Public       //
//  License. Please contact the author at herve.bitteur@laposte.net           //
//  to report bugs & suggestions.                                             //
//----------------------------------------------------------------------------//
//
package omr.score.visitor;

import omr.glyph.Shape;
import static omr.glyph.Shape.*;

import omr.score.Barline;
import omr.score.Clef;
import omr.score.KeySignature;
import omr.score.Measure;
import omr.score.MusicNode;
import omr.score.Score;
import omr.score.Slur;
import omr.score.Staff;
import omr.score.StaffNode;
import omr.score.System;
import omr.score.TimeSignature;

import omr.util.Logger;

/**
 * Class <code>ScoreChecker</code> can visit the score hierarchy perform
 * global checking on score nodes.
 * 
 * 
 * @author Herv&eacute Bitteur
 * @version $Id$
 */
public class ScoreChecker
    implements Visitor
{
    //~ Static fields/initializers ---------------------------------------------

    private static final Logger logger = Logger.getLogger(
        ScoreChecker.class);

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ScoreChecker object.
     */
    public ScoreChecker ()
    {
    }

    //~ Methods ----------------------------------------------------------------

    public boolean visit (Barline barline)
    {
        return true;
    }

    public boolean visit (Clef clef)
    {
        return true;
    }

    public boolean visit (KeySignature keySignature)
    {
        return true;
    }

    public boolean visit (Measure measure)
    {
        return true;
    }

    public boolean visit (MusicNode musicNode)
    {
        return true;
    }

    public boolean visit (Score score)
    {
        if (logger.isFineEnabled()) {
            logger.fine("Checking score ...");
        }

        score.acceptChildren(this);

        return false;
    }

    public boolean visit (Slur slur)
    {
        return true;
    }

    public boolean visit (Staff staff)
    {
        return true;
    }

    public boolean visit (StaffNode staffNode)
    {
        return true;
    }

    public boolean visit (System system)
    {
        return true;
    }

    public boolean visit (TimeSignature timeSignature)
    {
        if (logger.isFineEnabled()) {
            logger.fine("Checking " + timeSignature);
        }

        Shape shape = timeSignature.getShape();

        if (shape == null) {
            logger.warning("CheckNode. Time signature with no assigned shape");
        } else if (shape == NO_LEGAL_SHAPE) {
            logger.warning("CheckNode. Illegal " + this);
        } else if (Shape.SingleTimes.contains(shape)) {
            logger.warning("CheckNode. Orphan time signature shape : " + shape);
        }

        return true;
    }
}