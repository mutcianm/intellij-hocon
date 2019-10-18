package org.jetbrains.plugins.hocon
package editor
package moveStatement

import com.intellij.openapi.actionSystem.IdeActions.ACTION_MOVE_STATEMENT_DOWN_ACTION

/**
  * @author ghik
  */
class HoconMoveStatementDownActionTest extends HoconEditorActionTest(ACTION_MOVE_STATEMENT_DOWN_ACTION, "moveStatement/both")
object HoconMoveStatementDownActionTest extends TestSuiteCompanion[HoconMoveStatementDownActionTest]
