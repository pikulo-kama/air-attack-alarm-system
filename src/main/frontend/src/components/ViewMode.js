import {useDispatch, useSelector} from "react-redux";
import {switchMode, getActiveModeDataSelector} from "../features/viewModeSlice";

const ViewMode = () => {

    const dispatch = useDispatch()

    const activeModeData = useSelector(getActiveModeDataSelector)
    const Icon = activeModeData.icon

    return (
        <button
            className='app-button'
            onClick={() => dispatch(switchMode())}
        >
            <Icon
                size={25}
                style={{
                    color: activeModeData.iconColor
                }}
            />
        </button>
    )
}

export default ViewMode