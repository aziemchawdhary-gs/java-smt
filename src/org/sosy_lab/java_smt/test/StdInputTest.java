/*
 *  JavaSMT is an API wrapper for a collection of SMT solvers.
 *  This file is part of JavaSMT.
 *
 *  Copyright (C) 2007-2016  Dirk Beyer
 *  All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.sosy_lab.java_smt.test;

import org.sosy_lab.common.ShutdownManager;
import org.sosy_lab.common.configuration.Configuration;
import org.sosy_lab.common.configuration.InvalidConfigurationException;
import org.sosy_lab.common.log.BasicLogManager;
import org.sosy_lab.common.log.LogManager;
import org.sosy_lab.java_smt.SolverContextFactory;
import org.sosy_lab.java_smt.SolverContextFactory.Solvers;
import org.sosy_lab.java_smt.api.BooleanFormula;
import org.sosy_lab.java_smt.api.FormulaManager;
import org.sosy_lab.java_smt.api.ProverEnvironment;
import org.sosy_lab.java_smt.api.SolverContext;


public class StdInputTest {


  public static void main(String[] args) throws InvalidConfigurationException,
                                                InterruptedException {
    Configuration config = Configuration.fromCmdLineArguments(args);
    LogManager logger = BasicLogManager.create(config);
    ShutdownManager shutdown = ShutdownManager.create();

    SolverContext context = SolverContextFactory.createSolverContext(
        config, logger, shutdown.getNotifier(), Solvers.SMTINTERPOL);
    String header = "(declare-fun x4_plus () Int)\n"
        + "(declare-fun x4_minus () Int)\n"
        + "(declare-fun x2_plus () Int)\n"
        + "(declare-fun x2_minus () Int)\n"
        + "(declare-fun x1_plus () Int)\n"
        + "(declare-fun x1_minus () Int)\n"
        + "(declare-fun x0_plus () Int)\n"
        + "(declare-fun x0_minus () Int)\n"
        + "(declare-fun x3_plus () Int)\n"
        + "(declare-fun x3_minus () Int)";
    String firstAssert = "(assert (>= x4_plus 0))";
    FormulaManager fmgr = context.getFormulaManager();
    //BooleanFormulaManager bmgr = fmgr.getBooleanFormulaManager();
    //IntegerFormulaManager imgr = fmgr.getIntegerFormulaManager();
    BooleanFormula constraint = fmgr.parse(header + firstAssert);
    System.out.println(constraint.toString());
    try (ProverEnvironment pe = context.newProverEnvironment()) {
      pe.push(constraint);
    }
  }
}