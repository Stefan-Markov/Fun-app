import './navBarStyle.css';
import {NavLink} from 'react-router-dom';


const NavBar = ({isAuthenticated, user}) => {

    try {
        let yes = (
            <>
                <NavLink to={'/'}>
                    <li className={'navbar'}> Home <i className="fas fa-home"></i></li>
                </NavLink>
                <NavLink to={'/joke-add'}>
                    <li className={'navbar'}> Add joke <i className="fas fa-plus-square"></i></li>
                </NavLink>
                <NavLink to={'/account'}>
                    <li className={'navbar'}> Account <i className="fas fa-user-circle"></i></li>
                </NavLink>
                <NavLink to={'/joke-find'}>
                    <li className={'navbar'}> Find joke <i className="fas fa-puzzle-piece"></i></li>
                </NavLink>
                <NavLink to={`/joke-manage`}>
                    <li className={'navbar'}> Joke manage <i className="fas fa-tasks"></i></li>
                </NavLink>
                <NavLink to={'/logout'}>
                    <li className={'navbar'}> Logout <i className="fas fa-door-open"></i></li>
                </NavLink>
            </>
        );

        let no = (
            <>
                <NavLink to={'/login'}>
                    <li className={'navbar'}>Login <i className="fas fa-sign-in-alt"></i></li>
                </NavLink>
                <NavLink to={'/register'}>
                    <li className={'navbar'}>
                        Register <i className="fas fa-arrow-circle-up"></i>
                    </li>
                </NavLink>
                <NavLink to={'/'}>
                    <li className={'navbar'}> Home <i className="fas fa-home"></i></li>
                </NavLink>
            </>
        );

        let admin = (
            <>
                <NavLink to={'/admin'}>
                    <li className={'navbar'}> Admin panel <i className="fas fa-user-shield"></i></li>
                </NavLink>
            </>
        );

        const isAdmin = () => {
            let roles = sessionStorage.getItem('roles')
            if (roles) {
                return roles.includes('ROLE_ADMIN');
            }
            return null;
        }

        return <header className={'navbar top'}>
            <nav>
                <ul> {
                    isAuthenticated ? yes : no
                }
                    {isAdmin() ? admin : ''}
                </ul>
            </nav>
        </header>;
    } catch (err) {

    }
}

export default NavBar;
